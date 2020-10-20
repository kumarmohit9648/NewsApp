package com.knovatik.navadesh.ui.activity

import android.os.Bundle
import android.view.View
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.knovatik.navadesh.databinding.ActivityForgetPasswordBinding
import com.knovatik.navadesh.ui.BaseActivity
import com.knovatik.navadesh.util.hideKeyboard
import com.knovatik.navadesh.util.setErrorWithFocus
import com.knovatik.navadesh.util.snackbar
import org.json.JSONObject

class ForgetPasswordActivity : BaseActivity() {

    private lateinit var binding: ActivityForgetPasswordBinding
    private var mobile = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.sendOtpConstraint.visibility = View.VISIBLE

        binding.btnSendOtp.setOnClickListener {
            hideKeyboard(it)
            when {
                binding.inputMobile.text.toString().isEmpty() -> {
                    binding.inputMobile.setErrorWithFocus("Enter Mobile Number")
                }
                binding.inputMobile.text.toString().length != 10 -> {
                    binding.inputMobile.setErrorWithFocus("Enter proper mobile number")
                }
                else -> {
                    mobile = binding.inputMobile.text.toString()
                    sendOtp(binding.inputMobile.text.toString())
                }
            }
        }

        binding.btnVerifyOtp.setOnClickListener {
            hideKeyboard(it)
            verifyOtp(binding.tvOtp.text.toString())
        }

        binding.btnResetPassword.setOnClickListener {
            hideKeyboard(it)
            if (binding.tvPassword.text.isNullOrEmpty())
                binding.tvPassword.setErrorWithFocus("Enter password")
            else if (binding.tvPassword.text.isNullOrEmpty())
                binding.tvPassword.setErrorWithFocus("Enter re-password")
            else if (!binding.tvPassword.text.toString()
                    .equals(binding.tvRePassword.text.toString(), ignoreCase = true)
            ) {
                binding.root.snackbar("Password not match")
            } else {
                resetPassword(binding.tvPassword.text.toString())
            }
        }
    }

    private fun resetPassword(password: String) {
        binding.progressBar.visibility = View.VISIBLE

        val url = "http://dbpnews.knovatik.com/Api/" + "forgot-password"

        val request = JSONObject()
        request.put("mobile_no", mobile)
        request.put("password", password)

        AndroidNetworking.post(url)
            .addJSONObjectBody(request)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    binding.progressBar.visibility = View.GONE
                    if (response.getBoolean("status")) {
                        binding.root.snackbar("Success")
                        finish()
                    } else {
                        binding.root.snackbar(response.getString("message"))
                    }
                }

                override fun onError(error: ANError?) {
                    binding.progressBar.visibility = View.GONE
                    binding.root.snackbar("Error occurred while saving data")
                }
            })
    }

    private fun verifyOtp(otp: String) {
        binding.progressBar.visibility = View.VISIBLE

        val url = "http://dbpnews.knovatik.com/Api/" + "verify-otp"

        val request = JSONObject()
        request.put("mobile_no", mobile)
        request.put("otp", otp)

        AndroidNetworking.post(url)
            .addJSONObjectBody(request)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    binding.progressBar.visibility = View.GONE
                    if (response.getBoolean("status")) {
                        binding.root.snackbar("Success")
                        binding.verifyOtpConstraint.visibility = View.GONE
                        binding.changePasswordConstraint.visibility = View.GONE
                    } else {
                        binding.root.snackbar(response.getString("message"))
                    }
                }

                override fun onError(error: ANError?) {
                    binding.progressBar.visibility = View.GONE
                    binding.root.snackbar("Error occurred while saving data")
                }
            })
    }

    private fun sendOtp(mobile: String) {
        binding.progressBar.visibility = View.VISIBLE

        val url = "http://dbpnews.knovatik.com/Api/" + "send-forgot-password-otp"

        val request = JSONObject()
        request.put("mobile_no", mobile)

        AndroidNetworking.post(url)
            .addJSONObjectBody(request)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    binding.progressBar.visibility = View.GONE
                    if (response.getBoolean("status")) {
                        binding.root.snackbar("Success")
                        binding.sendOtpConstraint.visibility = View.GONE
                        binding.verifyOtpConstraint.visibility = View.VISIBLE
                    } else {
                        binding.root.snackbar(response.getString("message"))
                    }
                }

                override fun onError(error: ANError?) {
                    binding.progressBar.visibility = View.GONE
                    binding.root.snackbar(error?.errorBody!!)
                }
            })
    }

}