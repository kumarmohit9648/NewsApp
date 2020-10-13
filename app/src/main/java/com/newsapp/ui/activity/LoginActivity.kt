package com.newsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.ActivityLoginBinding
import com.newsapp.model.login.LoginRequest
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.vm.LoginViewModel
import com.newsapp.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    companion object {
        private const val TAG = "LoginActivity"
    }

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        viewModel.loginResponse.observe(this, {
            try {
                if (it.status) {
                    if (it.data != null) {
                        Prefs.putString(AppConstant.AUTH_TOKEN, it.data.token)
                        Prefs.putBoolean(AppConstant.IS_LOGIN, true)
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                DashboardActivity::class.java
                            )
                        )
                        finishAffinity()
                    }
                } else
                    toast(it.message)
            } catch (e: Exception) {
            } finally {
                binding.progressBar.visibility = View.VISIBLE
            }
        })

        binding.login.setOnClickListener {
            if (validation()) {
                viewModel.login(
                    LoginRequest(
                        binding.userPassword.text.trim().toString(),
                        binding.username.text.trim().toString()

                    )
                )
                binding.progressBar.visibility = View.VISIBLE
            }
        }

    }

    private fun validation(): Boolean {
        if (binding.username.text.isNullOrEmpty()) {
            binding.username.error = "Invalid Username"
            return false
        } else if (binding.userPassword.text.isNullOrEmpty()) {
            binding.username.error = "Invalid Password"
            return false
        }
        return true
    }

}