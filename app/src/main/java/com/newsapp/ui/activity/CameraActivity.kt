package com.newsapp.ui.activity

import android.os.Bundle
import com.newsapp.R
import com.newsapp.ui.BaseActivity
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.VideoResult
import com.otaliastudios.cameraview.controls.Mode
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        camera.setLifecycleOwner(this)

        camera.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {

            }

            override fun onVideoTaken(result: VideoResult) {

            }
        })
        camera.takePicture()
        camera.mode = Mode.VIDEO

    }

}