package com.knovatik.navadesh.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.knovatik.navadesh.BuildConfig
import com.knovatik.navadesh.R
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.databinding.ActivityGeneratePostBinding
import com.knovatik.navadesh.ui.vm.GeneratePostViewModel
import com.knovatik.navadesh.util.compressImageFile
import com.knovatik.navadesh.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

@AndroidEntryPoint
class GeneratePostActivity : ParentActivity() {

    companion object {
        private const val TAG = "GeneratePostActivity"
        private const val IS_PHOTO = "91"
        private const val IS_VIDEO = "92"
        private const val IS_AUDIO = "93"
        // private const val TAKE_PHOTO = "1"
        // private const val TAKE_VIDEO = "2"
        // private const val BROWSE_PHOTO = "3"
        // private const val BROWSE_VIDEO = "4"
        // private const val BROWSE_AUDIO = "5"
        // private const val REQUEST_PHOTO = 97
        // private const val REQUEST_VIDEO = 98
        // private const val REQUEST_AUDIO = 99

        private const val REQ_CAPTURE = 100
        private const val RES_IMAGE = 100
    }

    private var queryImageUrl: String = ""
    private var imgPath: String = ""
    private var imageUri: Uri? = null
    private val permissions = arrayOf(Manifest.permission.CAMERA)

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

        binding.btnImage.setOnClickListener {
            if (isPermissionsAllowed(permissions, true, REQ_CAPTURE)) {
                chooseImage()
            }
        }

        binding.btnVideo.setOnClickListener {
            /*if (isPermissionsAllowed(permissions, true, REQ_CAPTURE)) {
                chooseImage()
            }*/
        }

        binding.btnAudio.setOnClickListener {
            /*if (isPermissionsAllowed(permissions, true, REQ_CAPTURE)) {
                chooseImage()
            }*/
        }

//        binding.btnImageDirect.setOnClickListener {
//            _chooseFile = TAKE_PHOTO
//            binding.chooseFile.text = "इमेज उप्लोडेड"
//            startActivity(
//                Intent(this, CameraActivity::class.java)
//                    .putExtra(AppConstant.CAMERA_TYPE, AppConstant.PHOTO)
//            )
//        }
//        binding.btnImageFile.setOnClickListener {
//            _chooseFile = BROWSE_PHOTO
//            binding.chooseFile.text = "इमेज उप्लोडेड"
//            val intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_PHOTO)
//        }
//        binding.btnVideoDirect.setOnClickListener {
//            _chooseFile = TAKE_VIDEO
//            binding.chooseFile.text = "वीडियो उप्लोडेड"
//            startActivity(
//                Intent(this, CameraActivity::class.java)
//                    .putExtra(AppConstant.CAMERA_TYPE, AppConstant.VIDEO)
//            )
//        }
//        binding.btnVideoFile.setOnClickListener {
//            _chooseFile = BROWSE_VIDEO
//            binding.chooseFile.text = "वीडियो उप्लोडेड"
//            val intent = Intent()
//            intent.type = "video/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_VIDEO)
//        }
//        binding.btnAudioFile.setOnClickListener {
//            _chooseFile = BROWSE_AUDIO
//            binding.chooseFile.text = "ऑडियो उप्लोडेड"
//            val intent = Intent()
//            intent.type = "audio/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_AUDIO)
//        }

        binding.btnSubmit.setOnClickListener {
            if (validation()) {
                try {
                    var photo: File? = null
                    var video: File? = null
                    var audio: File? = null

                    /*if (_chooseFile == TAKE_PHOTO) {
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
                    }*/

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

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (resultCode == RESULT_OK) {
                when (requestCode) {
                    REQUEST_PHOTO -> {
                        val path = FilePath.getPath(this, data.data)
                        _file = File(path)
                        *//*data.data.let { returnUri ->
                            contentResolver.query(returnUri!!, null, null, null, null)
                                ?.use { cursor ->

                                }
                        }*//*
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
    }*/

    // NEW CODE

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQ_CAPTURE -> {
                if (isAllPermissionsGranted(grantResults)) {
                    chooseImage()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.permission_not_granted),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RES_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    handleImageRequest(data)
                }
            }
        }
    }

    private fun chooseImage() {
        startActivityForResult(getPickImageIntent(), RES_IMAGE)
    }

    private fun getPickImageIntent(): Intent? {
        var chooserIntent: Intent? = null

        var intentList: MutableList<Intent> = ArrayList()

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri())

        intentList = addIntentsToList(this, intentList, pickIntent)
        intentList = addIntentsToList(this, intentList, takePhotoIntent)

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(
                intentList.removeAt(intentList.size - 1),
                getString(R.string.select_capture_image)
            )
            chooserIntent!!.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                intentList.toTypedArray<Parcelable>()
            )
        }

        return chooserIntent
    }

    private fun setImageUri(): Uri {
        val folder = File("${getExternalFilesDir(Environment.DIRECTORY_DCIM)}")
        folder.mkdirs()

        val calendar = AppConstant.simpleDateFormat.format(Calendar.getInstance().time)

        val file = File(folder, "Image_Tmp.jpg")
        if (file.exists())
            file.delete()
        file.createNewFile()
        imageUri = FileProvider.getUriForFile(
            this,
            BuildConfig.APPLICATION_ID + getString(R.string.file_provider_name),
            file
        )
        imgPath = file.absolutePath
        return imageUri!!
    }


    private fun addIntentsToList(
        context: Context,
        list: MutableList<Intent>,
        intent: Intent
    ): MutableList<Intent> {
        val resInfo = context.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resInfo) {
            val packageName = resolveInfo.activityInfo.packageName
            val targetedIntent = Intent(intent)
            targetedIntent.setPackage(packageName)
            list.add(targetedIntent)
        }
        return list
    }

    private fun handleImageRequest(data: Intent?) {
        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
            binding.progressBar.visibility = View.GONE
            Toast.makeText(
                this,
                t.localizedMessage ?: getString(R.string.some_err),
                Toast.LENGTH_SHORT
            ).show()
        }

        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            binding.progressBar.visibility = View.VISIBLE

            if (data?.data != null) {     //Photo from gallery
                imageUri = data.data
                queryImageUrl = imageUri?.path!!
                queryImageUrl = compressImageFile(queryImageUrl, false, imageUri!!)
            } else {
                queryImageUrl = imgPath ?: ""
                compressImageFile(queryImageUrl, uri = imageUri!!)
            }
            imageUri = Uri.fromFile(File(queryImageUrl))

            if (queryImageUrl.isNotEmpty()) {
                /*Glide.with(this@GeneratePostActivity)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .load(queryImageUrl)
                    .into(iv_img)*/

            }
            binding.progressBar.visibility = View.GONE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        AppConstant.pictureResult = null
        AppConstant.videoResult = null
    }

}