package com.newsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.ActivityGeneratePostBinding
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.camera.CameraActivity
import com.newsapp.ui.vm.GeneratePostViewModel
import com.newsapp.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class GeneratePostActivity : BaseActivity() {

    companion object {
        private const val TAG = "GeneratePostActivity"
        private const val TAKE_PHOTO = "1"
        private const val TAKE_VIDEO = "2"
        private const val BROWSE_PHOTO = "3"
        private const val BROWSE_VIDEO = "4"
        private const val BROWSE_AUDIO = "5"
        private const val REQUEST_PHOTO = 97
        private const val REQUEST_VIDEO = 98
        private const val REQUEST_AUDIO = 99
    }

    private lateinit var _inputMediaType: String
    private lateinit var binding: ActivityGeneratePostBinding
    private val viewModel: GeneratePostViewModel by viewModels()

    private var chooseFile: String? = null
    private var _fileName: String? = null
    private var fileName = Calendar.getInstance().time.toString() + ".mp3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneratePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.titleName.text = "Make Post"

        Glide.with(this).load(R.drawable.ic_close).into(binding.toolbar.ivBack)
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }

        viewModel.uploadContentResponse.observe(this, {
            try {
                binding.progressBar.visibility = View.GONE
                if (it.status)
                    finish()
                toast(it.message)
            } catch (e: Exception) {
            }
        })

        binding.btnImageDirect.setOnClickListener {
            chooseFile = TAKE_PHOTO
            binding.chooseFile.text = "इमेज उप्लोडेड"
            startActivity(
                Intent(this, CameraActivity::class.java)
                    .putExtra(AppConstant.CAMERA_TYPE, AppConstant.PHOTO)
            )
        }
        binding.btnImageFile.setOnClickListener {
            chooseFile = BROWSE_PHOTO
            binding.chooseFile.text = "इमेज उप्लोडेड"
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_PHOTO)
        }
        binding.btnVideoDirect.setOnClickListener {
            chooseFile = TAKE_VIDEO
            binding.chooseFile.text = "वीडियो उप्लोडेड"
            startActivity(
                Intent(this, CameraActivity::class.java)
                    .putExtra(AppConstant.CAMERA_TYPE, AppConstant.VIDEO)
            )
        }
        binding.btnVideoFile.setOnClickListener {
            chooseFile = BROWSE_VIDEO
            binding.chooseFile.text = "वीडियो उप्लोडेड"
            val intent = Intent()
            intent.type = "video/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_VIDEO)
        }
        binding.btnAudioFile.setOnClickListener {
            chooseFile = BROWSE_AUDIO
            binding.chooseFile.text = "ऑडियो उप्लोडेड"
            val intent = Intent()
            intent.type = "audio/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_AUDIO)
        }

        binding.btnSubmit.setOnClickListener {
            if (validation()) {
                viewModel.uploadContent(
                    Prefs.getString(AppConstant.AUTH_TOKEN, ""),
                    binding.edTitle.text.toString().trim(),
                    binding.edDescription.text.toString().trim(),
                    binding.etState.text.toString().trim(),
                    binding.etDistrict.text.toString().trim(),
                    binding.idVillage.text.toString().trim(),
                    binding.idAddress.text.toString().trim(),
                )
                binding.progressBar.visibility = View.VISIBLE
            }
        }

    }

    private fun validation(): Boolean {
        when {
            binding.edTitle.text.isNullOrEmpty() -> {
                binding.edTitle.error = "अमान्य"
                return false
            }
            binding.edDescription.text.isNullOrEmpty() -> {
                binding.edDescription.error = "अमान्य"
                return false
            }
            binding.edDescription.text.toString().length < 50 -> {
                binding.edDescription.error = "कम से कम 50 शब्द"
                return false
            }
            binding.etState.text.isNullOrEmpty() -> {
                binding.etState.error = "अमान्य"
                return false
            }
            binding.etDistrict.text.isNullOrEmpty() -> {
                binding.etDistrict.error = "अमान्य"
                return false
            }
            binding.idAddress.text.isNullOrEmpty() -> {
                binding.idAddress.error = "अमान्य"
                return false
            }
            else -> return true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_PHOTO -> {

                }
                REQUEST_AUDIO -> {

                }
                REQUEST_VIDEO -> {

                }
            }
        }
    }

}