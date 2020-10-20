package com.knovatik.navadesh.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import com.asksira.bsimagepicker.BSImagePicker
import com.asksira.bsimagepicker.Utils
import com.bumptech.glide.Glide
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.databinding.ActivityMyProfileBinding
import com.knovatik.navadesh.model.profile.Data
import com.knovatik.navadesh.model.profile.UpdateProfileImage
import com.knovatik.navadesh.ui.BaseActivity
import com.knovatik.navadesh.ui.vm.MyProfileViewModel
import com.knovatik.navadesh.util.getEncoded64ImageStringFromBitmap
import com.knovatik.navadesh.util.snackbar
import com.knovatik.navadesh.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileActivity : BaseActivity(),
    BSImagePicker.OnSingleImageSelectedListener,
    BSImagePicker.OnMultiImageSelectedListener,
    BSImagePicker.ImageLoaderDelegate,
    BSImagePicker.OnSelectImageCancelledListener {

    companion object {
        private const val TAG = "MyProfileActivity"
        const val PROFILE_IMAGE = "64654"
    }

    private lateinit var binding: ActivityMyProfileBinding
    private val viewModel: MyProfileViewModel by viewModels()
    private var userDetail: Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }

        viewModel.getProfileResponse.observe(this, {
            if (it.status) {
                binding.btnUpdateProfile.isClickable = true
                if (it.data != null) {
                    userDetail = it.data
                    if (!it.data.profile_image.isNullOrEmpty())
                        Glide.with(this).load(it.data.profile_image).into(binding.userProfileImage)
                    binding.userName.text = it.data.full_name
                    binding.mobileNumber.text = it.data.mobile_no
                    binding.email.text = it.data.email_id
                    binding.gender.text = it.data.gender
                    if (it.data.pin_code == "0")
                        binding.address.text =
                            "${it.data.address} ${it.data.city} ${it.data.state} ${it.data.country}"
                    else
                        binding.address.text =
                            "${it.data.address} ${it.data.city} ${it.data.state} ${it.data.country}\nPin Code: ${it.data.pin_code}"
                }
            }
        })

        binding.changeProfile.setOnClickListener {
            val singleSelectionPicker: BSImagePicker =
                BSImagePicker.Builder("com.newsapp.fileprovider")
                    .setMaximumDisplayingImages(Integer.MAX_VALUE) // Default: Integer.MAX_VALUE. Don't worry about performance :)
                    .setSpanCount(3) // Default: 3. This is the number of columns
                    .setGridSpacing(Utils.dp2px(2)) // Default: 2dp. Remember to pass in a value in pixel.
                    .setPeekHeight(Utils.dp2px(360)) // Default: 360dp. This is the initial height of the dialog.
                    .hideGalleryTile() // Default: show. Set this if you don't want to further let user select from a gallery app. In such case, I suggest you to set maximum displaying images to Integer.MAX_VALUE.
                    .setTag(PROFILE_IMAGE) // Default: null. Set this if you need to identify which picker is calling back your fragment / activity.
                    .useFrontCamera() // Default: false. Launching camera by intent has no reliable way to open front camera so this does not always work.
                    .build()

            singleSelectionPicker.show(supportFragmentManager, "PICKER")
        }

        binding.btnUpdateProfile.setOnClickListener {
            startActivity(
                Intent(
                    this@MyProfileActivity,
                    UpdateProfileActivity::class.java
                ).putExtra(AppConstant.PROFILE_DETAIL, userDetail)
            )
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.getProfile()
    }

    private fun setImageProfile(uri: Uri?) {
        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        viewModel.imageProfileResponse.observe(this, {
            if (it.status) {
                binding.progressBar.visibility = View.GONE
                Glide.with(this@MyProfileActivity).load(uri).into(binding.userProfileImage)
                binding.root.snackbar(it.message)
            } else
                toast(it.message)
        })
        viewModel.imageProfile(
            UpdateProfileImage(
                Prefs.getString(AppConstant.AUTH_TOKEN, ""),
                getEncoded64ImageStringFromBitmap(bitmap)!!
            )
        )
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onSingleImageSelected(uri: Uri?, tag: String?) {
        when (tag) {
            PROFILE_IMAGE -> {
                setImageProfile(uri)
            }
        }
    }

    override fun onMultiImageSelected(uriList: MutableList<Uri>?, tag: String?) {

    }

    override fun loadImage(imageUri: Uri?, ivImage: ImageView?) {
        Glide.with(this@MyProfileActivity).load(imageUri).into(ivImage!!)
    }

    override fun onCancelled(isMultiSelecting: Boolean, tag: String?) {

    }

}