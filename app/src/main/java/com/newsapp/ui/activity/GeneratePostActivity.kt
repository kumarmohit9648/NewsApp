package com.newsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.constants.AppConstant
import com.newsapp.ui.camera.CameraActivity
import kotlinx.android.synthetic.main.activity_generate_post.*
import kotlinx.android.synthetic.main.toolbar.*

class GeneratePostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_post)

        titleName.text = "Make Post"

        Glide.with(this).load(R.drawable.ic_close).into(ivBack)
        ivBack.setOnClickListener {
            finish()
        }

        imageView4.setOnClickListener {
            startActivity(
                Intent(this, CameraActivity::class.java)
                    .putExtra(AppConstant.CAMERA_TYPE, AppConstant.VIDEO)
            )
        }
        imageView3.setOnClickListener {
            startActivity(
                Intent(this, CameraActivity::class.java)
                    .putExtra(AppConstant.CAMERA_TYPE, AppConstant.PHOTO)
            )
        }

    }
}