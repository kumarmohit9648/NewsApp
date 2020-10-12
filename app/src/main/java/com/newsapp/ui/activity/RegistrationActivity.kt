package com.newsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.ActivityRegistrationBinding
import com.newsapp.model.register.RegisterRequest
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.vm.RegisterViewModel
import com.newsapp.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationActivity : BaseActivity() {

    companion object {
        private const val TAG = "RegistrationActivity"
    }

    private lateinit var binding: ActivityRegistrationBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.register.setOnClickListener {
            viewModel.userRegistrationResponse.observe(this, Observer {
                try {
                    if (it.status) {
                        if (it.data != null) {
                            Prefs.putString(AppConstant.AUTH_TOKEN, it.data.token)
                            Prefs.putBoolean(AppConstant.IS_LOGIN, true)
                            startActivity(
                                Intent(
                                    this@RegistrationActivity,
                                    DashboardActivity::class.java
                                )
                            )
                            finishAffinity()
                        }
                    }
                } catch (e: Exception) {
                } finally {
                    binding.progressBar.visibility = View.VISIBLE
                }
            })
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