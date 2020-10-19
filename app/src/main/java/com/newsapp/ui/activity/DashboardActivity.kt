package com.newsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.newsapp.R
import com.newsapp.animation.ZoomOutPageTransformer
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.ActivityDashboardBinding
import com.newsapp.model.AuthToken
import com.newsapp.model.menu.Data
import com.newsapp.network.utils.Coroutines
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.adapter.MenuCategoryAdapter
import com.newsapp.ui.fragment.CitizenReporterFragment
import com.newsapp.ui.fragment.SubCategoryFragment
import com.newsapp.ui.fragment.TabFragment
import com.newsapp.ui.fragment.TimePassFragment
import com.newsapp.ui.vm.DashboardViewModel
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.navigation_drawer_dashboard.view.*
import java.util.*
import kotlin.math.min

@AndroidEntryPoint
class DashboardActivity : BaseActivity() {

    companion object {
        private const val TAG = "DashboardActivity"
    }

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        viewModel.getNotificationCount(AuthToken(Prefs.getString(AppConstant.AUTH_TOKEN, "")))
    }

    private fun getMenuCategory() {
        viewModel.getMenuCategoryResponse.observe(this, {
            try {
                if (it.status) {
                    if (it.data!!.isNotEmpty()) {
                        binding.drawerLayout.recyclerMenuCategory.adapter =
                            MenuCategoryAdapter(this, it.data)
                        setUpViewPager(binding.layoutDashboard.viewpager, it.data)
                    }
                }
            } catch (e: Exception) {
            }
        })
        viewModel.getMenuCategory()
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

        binding.drawerLayout.settings.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, SettingActivity::class.java))
        }

        binding.drawerLayout.profile.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, MyProfileActivity::class.java))
        }

    }

    private fun setUpViewPager(viewpager: ViewPager, menuCategories: List<Data>) {
        viewpager.setPageTransformer(true, ZoomOutPageTransformer())
        val adapter = ViewPagerAdapter(supportFragmentManager)
        for (i in menuCategories) {
            var fragment: Fragment? = null
            val bundle = Bundle()
            bundle.putString(AppConstant.CATEGORY_ID, i.id)
            when (i.is_menu) {
                "1" -> {
                    fragment = SubCategoryFragment.newInstance()
                    fragment.arguments = bundle
                    adapter.addFrag(fragment, i.name)
                }
                "2" -> {
                    fragment = TimePassFragment.newInstance()
                    fragment.arguments = bundle
                    adapter.addFrag(fragment, i.name)
                }
                "3" -> {
                    fragment = CitizenReporterFragment.newInstance(supportFragmentManager)
                    fragment.arguments = bundle
                    adapter.addFrag(fragment, i.name)
                }
                else -> {
                    fragment = TabFragment.newInstance()
                    fragment.arguments = bundle
                    adapter.addFrag(fragment, i.name)
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