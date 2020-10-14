package com.newsapp.ui.activity

import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.ActivityGeneratePostBinding
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.camera.CameraActivity
import java.io.IOException
import java.util.*

class GeneratePostActivity : BaseActivity() {

    companion object {
        private const val TAG = "GeneratePostActivity"
        private const val PHOTO = "1"
        private const val VIDEO = "2"
        private const val AUDIO = "3"
        private const val AUDIO_RECORD = "audio_record"
        private const val AUDIO_STOP = "audio_stop"
    }

    private lateinit var _inputMediaType: String
    private lateinit var binding: ActivityGeneratePostBinding

    private var _recorder: MediaRecorder? = null
    private var _player: MediaPlayer? = null
    private var _fileName: String? = null
    private var _audioStatus = AUDIO_RECORD
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

        binding.btnVideo.setOnClickListener {
            startActivity(
                Intent(this, CameraActivity::class.java)
                    .putExtra(AppConstant.CAMERA_TYPE, AppConstant.VIDEO)
            )
        }
        binding.btnImage.setOnClickListener {
            startActivity(
                Intent(this, CameraActivity::class.java)
                    .putExtra(AppConstant.CAMERA_TYPE, AppConstant.PHOTO)
            )
        }
        binding.btnAudio.setOnClickListener {
            try {
                var recorder: MediaRecorder? = null
                if (_audioStatus == AUDIO_RECORD) {
                    _audioStatus = AUDIO_STOP

                    recorder = MediaRecorder()
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                    recorder.setOutputFile(fileName)
                    try {
                        recorder.prepare()
                    } catch (e: IOException) {
                        Log.e(TAG, "prepare() failed")
                    }
                    recorder.start()
                } else {
                    _audioStatus = AUDIO_RECORD

                    recorder?.stop()
                    recorder?.release()
                    recorder = null
                }
            } catch (e: Exception) {
                Log.e(TAG, "onCreate: " + e.message)
            }
        }

    }
}