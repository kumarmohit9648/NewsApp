package com.knovatik.navadesh.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.knovatik.navadesh.BuildConfig
import com.knovatik.navadesh.R
import com.knovatik.navadesh.animation.ZoomOutPageTransformer
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.databinding.ActivityDashboardBinding
import com.knovatik.navadesh.model.AuthToken
import com.knovatik.navadesh.model.menu.Data
import com.knovatik.navadesh.model.weather.Weather
import com.knovatik.navadesh.network.interfaces.Api
import com.knovatik.navadesh.network.utils.Coroutines
import com.knovatik.navadesh.ui.BaseActivity
import com.knovatik.navadesh.ui.adapter.MenuCategoryAdapter
import com.knovatik.navadesh.ui.fragment.CitizenReporterFragment
import com.knovatik.navadesh.ui.fragment.SubCategoryFragment
import com.knovatik.navadesh.ui.fragment.TabFragment
import com.knovatik.navadesh.ui.fragment.TimePassFragment
import com.knovatik.navadesh.ui.vm.DashboardViewModel
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.min

@AndroidEntryPoint
class DashboardActivity : BaseActivity() {

    companion object {
        private const val TAG = "DashboardActivity"

        // location updates interval - 10sec
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

        // fastest updates interval - 5 sec
        // location updates will be received if another app is requesting the locations
        // than your app can handle
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = 5000

        private const val REQUEST_CHECK_SETTINGS = 100
    }

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()

    @Inject
    lateinit var okHttpClient: OkHttpClient

    // TODO: LOCATION MANAGER

    // TODO: LOCATION MANAGER
    // location last updated time
    private var mLastUpdateTime: String? = null

    // bunch of location related apis
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mCurrentLocation: Location? = null

    // boolean flag to toggle the ui
    private var mRequestingLocationUpdates: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize the necessary libraries
        init()

        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState)

        // Start Getting Location
        startLocation()

        setUpDrawer()
        clickListener()

        binding.layoutDashboard.tabs.setupWithViewPager(binding.layoutDashboard.viewpager)

        getMenuCategory()

        viewModel.getNotificationCountResponse.observe(this, {
            Coroutines.main {
                try {
                    if (it.status) {
                        setupBadge((it.data as Double).toInt())
                    }
                } catch (e: Exception) {
                }
            }
        })

    }

    override fun onStart() {
        super.onStart()
        Coroutines.main {
            viewModel.getNotificationCount(AuthToken(Prefs.getString(AppConstant.AUTH_TOKEN, "")))
        }
    }

    private fun init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mSettingsClient = LocationServices.getSettingsClient(this)
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                // location is received
                mCurrentLocation = locationResult.lastLocation
                mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
                updateLocation()
            }
        }
        mRequestingLocationUpdates = false
        mLocationRequest = LocationRequest()
        mLocationRequest!!.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS)
        mLocationRequest!!.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
        mLocationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        mLocationSettingsRequest = builder.build()
    }

    /**
     * Restoring values from saved instance state
     */
    private fun restoreValuesFromBundle(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates")
            }
            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location")
            }
            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on")
            }
        }
        updateLocation()
    }

    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    private fun updateLocation() {
        if (mCurrentLocation != null) {
            val addresses: List<Address>
            val geoCoder = Geocoder(this, Locale.getDefault())

            addresses = geoCoder.getFromLocation(
                mCurrentLocation!!.latitude,
                mCurrentLocation!!.longitude,
                1
            )
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            val address: String =
                addresses[0].getAddressLine(0)
            // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            val city: String = addresses[0].locality
            getWeather(city)
        }
    }

    private fun getWeather(district: String) {
        try {
            val key = "6daaaed95ed5407ca34104057202010"
            // val url = "http://api.weatherapi.com/v1/current.json?key=$key&q=$district"

            // Log.d(TAG, "getWeather: url: $url")

            val api = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)

            val request = api.weather(
                key,
                district
            )

            request.enqueue(object : Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    // Log.d(TAG, "onResponse: ${response.body()}")
                    val weather = response.body() ?: return
                    binding.drawer.weatherTemperature.text = " ${weather.current.temp_c}℃"
                    Glide.with(this@DashboardActivity)
                        .load("http:${weather.current.condition.icon}")
                        .into(binding.drawer.weatherImage)
                    if (weather.current.is_day.toInt() == 1) {
                        Glide.with(this@DashboardActivity)
                            .load(R.drawable.sunny)
                            .into(binding.drawer.weatherBackground)
                    } else {
                        Glide.with(this@DashboardActivity)
                            .load(R.drawable.night)
                            .into(binding.drawer.weatherBackground)
                    }
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    Log.e(TAG, "onFailure: ", t)
                }

            })

        } catch (e: Exception) {
            Log.e(TAG, "getWeather: ", e)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates!!)
        outState.putParcelable("last_known_location", mCurrentLocation)
        outState.putString("last_updated_on", mLastUpdateTime)
    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        mSettingsClient!!
            .checkLocationSettings(mLocationSettingsRequest)
            .addOnSuccessListener(this) {
                Log.i(TAG, "All location settings are satisfied.")

                // Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();
                mFusedLocationClient!!.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback, Looper.myLooper()
                )
                updateLocation()
            }
            .addOnFailureListener(this) { e ->
                when ((e as ApiException).statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Log.i(
                            TAG,
                            "Location settings are not satisfied. Attempting to upgrade " +
                                    "location settings "
                        )
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the
                            // result in onActivityResult().
                            val rae: ResolvableApiException = e as ResolvableApiException
                            rae.startResolutionForResult(
                                this@DashboardActivity,
                                REQUEST_CHECK_SETTINGS
                            )
                        } catch (sie: IntentSender.SendIntentException) {
                            Log.i(
                                TAG,
                                "PendingIntent unable to execute request."
                            )
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        val errorMessage = "Location settings are inadequate, and cannot be " +
                                "fixed here. Fix in Settings."
                        Log.e(TAG, errorMessage)
                        Toast.makeText(
                            this@DashboardActivity,
                            errorMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                updateLocation()
            }
    }

    private fun startLocation() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    mRequestingLocationUpdates = true
                    startLocationUpdates()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if (response.isPermanentlyDenied) {
                        // open device settings when the permission is
                        // denied permanently
                        openSettings()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun openSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts(
            "package",
            BuildConfig.APPLICATION_ID, null
        )
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates!! && checkPermissions()) {
            startLocationUpdates()
        }
        updateLocation()
    }

    private fun checkPermissions(): Boolean {
        val permissionState: Int = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    override fun onPause() {
        super.onPause()
        if (mRequestingLocationUpdates!!) {
            // pausing location updates
            stopLocationUpdates()
        }
    }

    private fun stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient!!
            .removeLocationUpdates(mLocationCallback)
            .addOnCompleteListener(this) {
                // Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
            }
    }

    private fun getMenuCategory() {
        viewModel.getMenuCategoryResponse.observe(this, {
            try {
                if (it.status) {
                    if (it.data!!.isNotEmpty()) {
                        binding.drawer.recyclerMenuCategory.adapter =
                            MenuCategoryAdapter(this, it.data)
                        setUpViewPager(binding.layoutDashboard.viewpager, it.data)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "getMenuCategory: ", e)
            } finally {
                binding.layoutDashboard.progressBar.visibility = View.GONE
            }
        })
        viewModel.getMenuCategory()
        binding.layoutDashboard.progressBar.visibility = View.VISIBLE
    }

    private fun setupBadge(count: Int) {
        if (count == 0) {
            if (binding.layoutDashboard.notificationCount.visibility != View.GONE) {
                binding.layoutDashboard.notificationCount.visibility = View.GONE
            }
        } else {
            binding.layoutDashboard.notificationCount.text = java.lang.String.valueOf(
                min(
                    count,
                    99
                )
            )
            if (binding.layoutDashboard.notificationCount.visibility != View.VISIBLE) {
                binding.layoutDashboard.notificationCount.visibility = View.VISIBLE
            }
        }
    }

    private fun clickListener() {

        binding.layoutDashboard.search.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, SearchActivity::class.java))
        }

        binding.layoutDashboard.notification.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, NotificationActivity::class.java))
        }

        binding.drawer.settings.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, SettingActivity::class.java))
        }

        binding.drawer.profile.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, MyProfileActivity::class.java))
        }

        binding.drawer.logout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Do you really want to logout? ")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    Prefs.putString(AppConstant.AUTH_TOKEN, "")
                    Prefs.putBoolean(AppConstant.IS_LOGIN, false)
                    startActivity(Intent(this@DashboardActivity, LoginOptionActivity::class.java))
                    finishAffinity()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }

    }

    private fun setUpViewPager(viewpager: ViewPager, menuCategories: List<Data>) {
        viewpager.setPageTransformer(true, ZoomOutPageTransformer())
        val adapter = ViewPagerAdapter(supportFragmentManager)
        for (i in 0..menuCategories.size - 3) {
            var fragment: Fragment? = null
            val bundle = Bundle()
            bundle.putString(AppConstant.CATEGORY_ID, menuCategories[i].id)
            when (menuCategories[i].is_menu) {
                "1" -> {
                    fragment = SubCategoryFragment.newInstance()
                    fragment.arguments = bundle
                    adapter.addFrag(fragment, menuCategories[i].name)
                }
                "2" -> {
                    fragment = TimePassFragment.newInstance()
                    fragment.arguments = bundle
                    adapter.addFrag(fragment, menuCategories[i].name)
                }
                "3" -> {
                    fragment = CitizenReporterFragment.newInstance(supportFragmentManager)
                    fragment.arguments = bundle
                    adapter.addFrag(fragment, menuCategories[i].name)
                }
                else -> {
                    fragment = TabFragment.newInstance()
                    fragment.arguments = bundle
                    adapter.addFrag(fragment, menuCategories[i].name)
                }
            }
        }
        viewpager.adapter = adapter
    }

    private fun setUpDrawer() {
        binding.layoutDashboard.menu.setImageResource(R.drawable.ic_menu)

        binding.layoutDashboard.menu.setOnClickListener {
            if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }

            binding.drawerLayout.drawerElevation = 0F

            binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
                override fun onDrawerStateChanged(p0: Int) {
                }

                override fun onDrawerSlide(p0: View, p1: Float) {

                }

                override fun onDrawerClosed(p0: View) {

                }

                override fun onDrawerOpened(p0: View) {
                    // setUpNavItems()
                }
            })
        }

    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerVisible(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            finish()
        }
    }

    class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(
            fragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {

        private val fragmentList = ArrayList<Fragment>()
        private val fragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList[position]
        }

    }
}