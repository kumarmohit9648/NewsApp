package com.knovatik.navadesh.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.viewModels
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.databinding.ActivityUpdateProfileBinding
import com.knovatik.navadesh.model.profile.Data
import com.knovatik.navadesh.model.profile.UpdateProfile
import com.knovatik.navadesh.ui.BaseActivity
import com.knovatik.navadesh.ui.vm.UpdateProfileViewModel
import com.knovatik.navadesh.util.hideKeyboard
import com.knovatik.navadesh.util.setErrorWithFocus
import com.knovatik.navadesh.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityUpdateProfileBinding
    private val viewModel: UpdateProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.titleName.text = "Update Profile"
        binding.toolbar.ivBack.setOnClickListener {
            onBackPressed()
        }

        val userDetail = intent.getSerializableExtra(AppConstant.PROFILE_DETAIL) as Data

        binding.userName.setText(userDetail.username)
        binding.userEmail.setText(userDetail.email)
        binding.userMobile.setText(userDetail.mobile_no)
        binding.userAddress.setText(userDetail.address)

        hideKeyboard(binding.root)

        viewModel.updateProfileResponse.observe(this, {
            binding.progressBar.visibility = View.GONE
            toast(it.message)
            if (it.status)
                onBackPressed()
        })

        binding.userBtn.setOnClickListener {
            if (validation()) {
                viewModel.updateProfile(
                    UpdateProfile(
                        Prefs.getString(AppConstant.AUTH_TOKEN, ""),
                        binding.userName.text.toString(),
                        binding.userAddress.text.toString()
                    )
                )
                binding.progressBar.visibility = View.VISIBLE
            }
        }

    }

    private fun validation(): Boolean {
        if (TextUtils.isEmpty(binding.userName.text)) {
            binding.userName.setErrorWithFocus("Enter Name")
            return false
        } else if (TextUtils.isEmpty(binding.userEmail.text)) {
            binding.userEmail.setErrorWithFocus("Enter Email")
            return false
        } else if (TextUtils.isEmpty(binding.userAddress.text)) {
            binding.userAddress.setErrorWithFocus("Enter Address")
            return false
        }
        return true
    }

}