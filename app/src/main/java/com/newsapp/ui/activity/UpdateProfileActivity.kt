package com.newsapp.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.viewModels
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.ActivityUpdateProfileBinding
import com.newsapp.model.profile.Data
import com.newsapp.model.profile.UpdateProfile
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.vm.UpdateProfileViewModel
import com.newsapp.util.hideKeyboard
import com.newsapp.util.setErrorWithFocus
import com.newsapp.util.toast
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

        binding.userName.setText(userDetail.full_name)
        binding.userEmail.setText(userDetail.email_id)
        // binding.user_number.setText(userDetail.mobile_no)
        binding.userAddress.setText(userDetail.address)
        binding.userPinCode.setText(userDetail.pin_code)
        binding.userCountry.setText(userDetail.country)
        binding.userState.setText(userDetail.state)
        binding.userCity.setText(userDetail.city)

        if (!userDetail.gender.isBlank()) {
            if (userDetail.gender.equals("male", ignoreCase = true))
                binding.male.isChecked = true
            else
                binding.female.isChecked = true
        }

        hideKeyboard(binding.root)

        viewModel.updateProfileResponse.observe(this, {
            binding.progressBar.visibility = View.GONE
            toast(it.message)
            if (it.status)
                onBackPressed()
        })

        binding.userBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            if (validation()) {

                val gender = if (binding.male.isChecked)
                    "Male"
                else
                    "Female"

                viewModel.updateProfile(
                    UpdateProfile(
                        binding.userAddress.text.toString(),
                        Prefs.getString(AppConstant.AUTH_TOKEN, ""),
                        binding.userCity.text.toString(),
                        binding.userCountry.text.toString(),
                        binding.userEmail.text.toString(),
                        gender,
                        binding.userName.text.toString(),
                        binding.userPinCode.text.toString(),
                        binding.userState.text.toString()
                    )
                )
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
        } /*else if (TextUtils.isEmpty(user_number.text)) {
            user_number.setErrorWithFocus("Enter Mobile number")
            return false
        }*/ else if (TextUtils.isEmpty(binding.userAddress.text)) {
            binding.userAddress.setErrorWithFocus("Enter Address")
            return false
        } else if (TextUtils.isEmpty(binding.userPinCode.text)) {
            binding.userPinCode.setErrorWithFocus("Enter Pin Code")
            return false
        } else if (TextUtils.isEmpty(binding.userCity.text)) {
            binding.userCity.setErrorWithFocus("Enter City")
            return false
        } else if (TextUtils.isEmpty(binding.userState.text)) {
            binding.userState.setErrorWithFocus("Enter State")
            return false
        } else if (TextUtils.isEmpty(binding.userCountry.text)) {
            binding.userCountry.setErrorWithFocus("Enter Country")
            return false
        }
        return true
    }

}