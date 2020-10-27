package com.knovatik.navadesh.ui.activity

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.knovatik.navadesh.R
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.databinding.ActivityGeneratePostBinding
import com.knovatik.navadesh.ui.vm.GeneratePostViewModel
import com.knovatik.navadesh.util.RealPathUtil
import com.knovatik.navadesh.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*

@AndroidEntryPoint
class GeneratePostActivity : ParentActivity() {

    companion object {
        private const val TAG = "GeneratePostActivity"
        const val PHOTO = 91
        const val VIDEO = 92
        const val AUDIO = 93
        // private const val TAKE_PHOTO = "1"
        // private const val TAKE_VIDEO = "2"
        // private const val BROWSE_PHOTO = "3"
        // private const val BROWSE_VIDEO = "4"
        // private const val BROWSE_AUDIO = "5"
        // private const val REQUEST_PHOTO = 97
        // private const val REQUEST_VIDEO = 98
        // private const val REQUEST_AUDIO = 99

        // private const val REQ_CAPTURE = 100
        // private const val RES_IMAGE = 100
    }

    private var photo: File? = null
    private var video: File? = null
    private var audio: File? = null

    // private var queryImageUrl: String = ""
    // private var imgPath: String = ""
    // private var imageUri: Uri? = null
    // private val permissions = arrayOf(Manifest.permission.CAMERA)

    private val viewModel: GeneratePostViewModel by viewModels()
    private lateinit var binding: ActivityGeneratePostBinding

    // private var _chooseFile: String? = null
    // private var _file: File? = null

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
            startActivityForResult(Intent(this, ImageActivity::class.java), PHOTO)
        }

        binding.btnVideo.setOnClickListener {
            startActivityForResult(Intent(this, VideoActivity::class.java), VIDEO)
        }

        binding.btnAudio.setOnClickListener {
            startActivityForResult(Intent(this, AudioActivity::class.java), AUDIO)
        }

        binding.btnSubmit.setOnClickListener {
            if (validation()) {
                try {
                    if (photo != null) {
                        val photoRequestFile: RequestBody =
                            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), photo!!)

                        val photoBody: MultipartBody.Part =
                            MultipartBody.Part.createFormData(
                                "profile_pic",
                                photo!!.name,
                                photoRequestFile
                            )

                        viewModel.uploadImageContent(
                            photoBody,
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
                    }

                    if (video != null) {
                        val videoRequestFile: RequestBody =
                            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), video!!)

                        val videoBody: MultipartBody.Part =
                            MultipartBody.Part.createFormData(
                                "profile_pic",
                                video!!.name,
                                videoRequestFile
                            )

                        viewModel.uploadVideoContent(
                            videoBody,
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
                    }


                    if (audio != null) {
                        val audioRequestFile: RequestBody =
                            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), audio!!)

                        val audioBody: MultipartBody.Part =
                            MultipartBody.Part.createFormData(
                                "profile_pic",
                                audio!!.name,
                                audioRequestFile
                            )

                        viewModel.uploadAudioContent(
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
                    }

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
        if (data == null) {
            return
        }
        when (requestCode) {
            PHOTO -> {
                // photo = saveFileIntoExternalStorageByUri(this, Uri.parse(data.getStringExtra("")!!))
                photo = RealPathUtil.getFileFromUri(this, Uri.parse(data.getStringExtra("PHOTO")))
            }
            AUDIO -> {
                audio = RealPathUtil.getFileFromUri(this, Uri.parse(data.getStringExtra("AUDIO")))
            }
            VIDEO -> {
                video = RealPathUtil.getFileFromUri(this, Uri.parse(data.getStringExtra("VIDEO")))
            }
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

    /*override fun onRequestPermissionsResult(
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
    }*/

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RES_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    handleImageRequest(data)
                }
            }
        }
    }*/

    /*private fun chooseImage() {
        startActivityForResult(getPickImageIntent(), RES_IMAGE)
    }*/

    /*private fun getPickImageIntent(): Intent? {
        var chooserIntent: Intent? = null

        var intentList: MutableList<Intent> = ArrayList()

        val pickIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

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
    }*/

    /*private fun setImageUri(): Uri {
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
    }*/


    /*private fun addIntentsToList(
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
    }*/

    /*private fun handleImageRequest(data: Intent?) {
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
                *//*Glide.with(this@GeneratePostActivity)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .load(queryImageUrl)
                    .into(iv_img)*//*

            }
            binding.progressBar.visibility = View.GONE
        }

    }*/

    fun makeEmptyFileIntoExternalStorageWithTitle(title: String?): File {
        val root: String = Environment.getExternalStorageDirectory().getAbsolutePath()
        return File(root, title)
    }

    fun getFileName(context: Context, uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    @Throws(java.lang.Exception::class)
    fun saveFileIntoExternalStorageByUri(context: Context, uri: Uri?): File? {
        val inputStream: InputStream = contentResolver.openInputStream(uri!!)!!
        val originalSize: Int = inputStream.available()
        var bis: BufferedInputStream? = null
        var bos: BufferedOutputStream? = null
        val fileName: String = getFileName(context, uri!!)
        val file: File = makeEmptyFileIntoExternalStorageWithTitle(fileName)
        bis = BufferedInputStream(inputStream)
        bos = BufferedOutputStream(
            FileOutputStream(
                file, false
            )
        )
        val buf = ByteArray(originalSize)
        bis.read(buf)
        do {
            bos.write(buf)
        } while (bis.read(buf) !== -1)
        bos.flush()
        bos.close()
        bis.close()
        return file
    }

    override fun onDestroy() {
        super.onDestroy()
        AppConstant.pictureResult = null
        AppConstant.videoResult = null
    }

}