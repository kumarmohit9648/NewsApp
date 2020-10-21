package com.knovatik.navadesh.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.databinding.ActivityRegisterBinding
import com.knovatik.navadesh.model.register.RegisterRequest
import com.knovatik.navadesh.ui.BaseActivity
import com.knovatik.navadesh.ui.vm.RegisterViewModel
import com.knovatik.navadesh.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {

    companion object {
        private const val TAG = "RegistrationActivity"
    }

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isSocial = intent.getBooleanExtra(AppConstant.IS_SOCIAL, false)
        val username = intent.getStringExtra(AppConstant.USERNAME)
        val email = intent.getStringExtra(AppConstant.EMAIL)

        if (isSocial) {
            binding.userName.setText(username)
            binding.userName.isEnabled = false
            binding.userEmail.setText(email)
            binding.userEmail.isEnabled = false
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        viewModel.userRegistrationResponse.observe(this, Observer {
            try {
                if (it.status) {
                    if (it.data != null) {
                        // Prefs.putString(AppConstant.AUTH_TOKEN, it.data.token)
                        // Prefs.putBoolean(AppConstant.IS_LOGIN, true)
                        startActivity(
                            Intent(
                                this@RegisterActivity,
                                ForgetPasswordActivity::class.java
                            )
                                .putExtra(AppConstant.MOBILE_NUMBER, it.data.mobile_no)
                                .putExtra(AppConstant.EMAIL, it.data.email)
                        )
                    }
                } else
                    toast(it.message)
            } catch (e: Exception) {
            } finally {
                binding.progressBar.visibility = View.VISIBLE
            }
        })

        binding.register.setOnClickListener {
            if (validation()) {
                viewModel.userRegistration(
                    RegisterRequest(
                        binding.userEmail.text.trim().toString(),
                        binding.userNumber.text.trim().toString(),
                        binding.userName.text.trim().toString(),
                        binding.userPassword.text.trim().toString(),
                    )
                )
                binding.progressBar.visibility = View.VISIBLE
            }
        }

    }

    private fun validation(): Boolean {
        if (binding.userName.text.isNullOrBlank()) {
            binding.userName.error = "Invalid Username"
            return false
        } else if (binding.userEmail.text.isNullOrBlank()) {
            binding.userEmail.error = "Invalid Email"
            return false
        } else if (binding.userNumber.text.length != 10) {
            binding.userNumber.error = "Invalid Number"
            return false
        } else if (binding.userPassword.text.isNullOrBlank()) {
            binding.userPassword.error = "Invalid Email"
            return false
        } else if (binding.userConfirmPassword.text.isNullOrBlank()) {
            binding.userConfirmPassword.error = "Invalid Email"
            return false
        } else if (binding.userPassword.text.trim() != binding.userConfirmPassword.text.trim()) {
            toast("Password does't match")
            return false
        }
        return true
    }

}