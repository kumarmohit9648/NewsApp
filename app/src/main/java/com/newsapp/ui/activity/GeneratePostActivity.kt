package com.newsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.ActivityGeneratePostBinding
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.camera.CameraActivity
import com.newsapp.ui.vm.GeneratePostViewModel
import com.newsapp.util.FilePath
import com.newsapp.util.Path
import com.newsapp.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
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

    private val viewModel: GeneratePostViewModel by viewModels()
    private lateinit var binding: ActivityGeneratePostBinding

    private var _chooseFile: String? = null
    private var _file: File? = null

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
            _chooseFile = TAKE_PHOTO
            binding.chooseFile.text = "इमेज उप्लोडेड"
            startActivity(
                Intent(this, CameraActivity::class.java)
                    .putExtra(AppConstant.CAMERA_TYPE, AppConstant.PHOTO)
            )
        }
        binding.btnImageFile.setOnClickListener {
            _chooseFile = BROWSE_PHOTO
            binding.chooseFile.text = "इमेज उप्लोडेड"
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_PHOTO)
        }
        binding.btnVideoDirect.setOnClickListener {
            _chooseFile = TAKE_VIDEO
            binding.chooseFile.text = "वीडियो उप्लोडेड"
            startActivity(
                Intent(this, CameraActivity::class.java)
                    .putExtra(AppConstant.CAMERA_TYPE, AppConstant.VIDEO)
            )
        }
        binding.btnVideoFile.setOnClickListener {
            _chooseFile = BROWSE_VIDEO
            binding.chooseFile.text = "वीडियो उप्लोडेड"
            val intent = Intent()
            intent.type = "video/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_VIDEO)
        }
        binding.btnAudioFile.setOnClickListener {
            _chooseFile = BROWSE_AUDIO
            binding.chooseFile.text = "ऑडियो उप्लोडेड"
            val intent = Intent()
            intent.type = "audio/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_AUDIO)
        }

        binding.btnSubmit.setOnClickListener {
            if (validation()) {
                try {
                    var photo: File? = null
                    var video: File? = null
                    var audio: File? = null

                    if (_chooseFile == TAKE_PHOTO) {
                        if (AppConstant.pictureResult != null) {
                            AppConstant.pictureResult!!.toBitmap(1000, 1000) {
                                try {
                                    photo = File(
                                        Environment.getExternalStorageDirectory(),
                                        AppConstant.simpleDateFormat.format(Calendar.getInstance().time)
                                    )
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
                    if (_chooseFile == TAKE_VIDEO) {
                        video = AppConstant.videoResult!!.file
                    }
                    if (_chooseFile == BROWSE_PHOTO) {
                        photo = _file
                    }
                    if (_chooseFile == BROWSE_VIDEO) {
                        video = _file
                    }
                    if (_chooseFile == BROWSE_AUDIO) {
                        audio = _file
                    }

                    var name = ""
                    if (photo == null)
                        photo = File("")
                    else
                        name = photo!!.name

                    val photoRequestFile: RequestBody =
                        RequestBody.create("multipart/form-data".toMediaTypeOrNull(), photo!!)

                    val photoBody: MultipartBody.Part =
                        MultipartBody.Part.createFormData(
                            "profile_pic",
                            name,
                            photoRequestFile
                        )

                    if (video == null)
                        video = File("")
                    else
                        name = video.name

                    val videoRequestFile: RequestBody =
                        RequestBody.create("multipart/form-data".toMediaTypeOrNull(), video)

                    val videoBody: MultipartBody.Part =
                        MultipartBody.Part.createFormData(
                            "profile_pic",
                            name,
                            videoRequestFile
                        )

                    if (audio == null)
                        audio = File("")
                    else
                        name = audio.name

                    val audioRequestFile: RequestBody =
                        RequestBody.create("multipart/form-data".toMediaTypeOrNull(), audio)

                    val audioBody: MultipartBody.Part =
                        MultipartBody.Part.createFormData(
                            "profile_pic",
                            name,
                            audioRequestFile
                        )


                    viewModel.uploadContent(
                        photoBody,
                        videoBody,
                        audioBody,
                        RequestBody.create(
                            "multipart/form-data".toMediaTypeOrNull(),
                            Prefs.getString(AppConstant.AUTH_TOKEN, "")
                        ),
                        RequestBody.create(
                            "multipart/form-data".toMediaTypeOrNull(),
                            binding.edTitle.text.toString().trim()
                        ),
                        RequestBody.create(
                            "multipart/form-data".toMediaTypeOrNull(),
                            binding.edDescription.text.toString().trim()
                        ),
                        RequestBody.create(
                            "multipart/form-data".toMediaTypeOrNull(),
                            binding.etState.text.toString().trim()
                        ),
                        RequestBody.create(
                            "multipart/form-data".toMediaTypeOrNull(),
                            binding.etDistrict.text.toString().trim()
                        ),
                        RequestBody.create(
                            "multipart/form-data".toMediaTypeOrNull(),
                            binding.idVillage.text.toString().trim()
                        ),
                        RequestBody.create(
                            "multipart/form-data".toMediaTypeOrNull(),
                            binding.idAddress.text.toString().trim()
                        ),
                    )
                    binding.progressBar.visibility = View.VISIBLE

                    /*AndroidNetworking.upload("http://dbpnews.knovatik.com/Api/upload-content")
                        .addMultipartFile("image_file", photo ?: File(""))
                        *//*  .addMultipartFile("video_file", video ?: File(""))
                          .addMultipartFile("audio_file", audio ?: File(""))*//*
                        .addMultipartParameter(
                            "auth_token",
                            Prefs.getString(AppConstant.AUTH_TOKEN, "")
                        )
                        .addMultipartParameter("title", binding.edTitle.text.toString().trim())
                        .addMultipartParameter(
                            "content",
                            binding.edDescription.text.toString().trim()
                        )
                        .addMultipartParameter("state", binding.etState.text.toString().trim())
                        .addMultipartParameter(
                            "district",
                            binding.etDistrict.text.toString().trim()
                        )
                        .addMultipartParameter(
                            "village",
                            binding.idVillage.text.toString().trim()
                        )
                        .addMultipartParameter(
                            "address",
                            binding.idAddress.text.toString().trim()
                        )
                        .setPriority(Priority.HIGH)
                        .build()
                        .setUploadProgressListener { bytesUploaded, totalBytes ->
                            Log.d(TAG, "bytesUploaded: $bytesUploaded")
                            // do anything with progress
                            *//*val per = (bytesUploaded.toDouble() / totalBytes.toDouble() * 100).toInt()
                            progressBar.max = 100
                            progressBar.progress = per*//*
                        }
                        .getAsJSONObject(object : JSONObjectRequestListener {
                            override fun onResponse(response: JSONObject) {
                                // do anything with response
                                binding.progressBar.visibility = View.GONE
                                Log.d(TAG, "onResponse: $response")
                                if (response.getBoolean("status")) {
                                    finish()
                                }
                                toast(response.getString("message"))
                            }

                            override fun onError(error: ANError) {
                                // handle error
                                binding.progressBar.visibility = View.GONE
                                toast(error.message!!)
                            }
                        })*/
                } catch (e: Exception) {
                    toast(e.message!!)
                }
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
        if (data != null) {
            if (resultCode == RESULT_OK) {
                when (requestCode) {
                    REQUEST_PHOTO -> {
                         val path = FilePath.getPath(this, data.data)
                        _file = File(path)
                        /*data.data.let { returnUri ->
                            contentResolver.query(returnUri!!, null, null, null, null)
                                ?.use { cursor ->

                                }
                        }*/
                    }
                    REQUEST_VIDEO -> {
                        _file = File(data.data?.path!!)
                    }
                    REQUEST_AUDIO -> {
                        _file = File(data.data?.path!!)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppConstant.pictureResult = null
        AppConstant.videoResult = null
    }

}