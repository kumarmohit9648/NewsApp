package com.newsapp.ui.activity

import android.os.Bundle
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.databinding.ActivityGeneratePostBinding
import com.newsapp.ui.BaseActivity

class GeneratePostActivity : BaseActivity() {

    private lateinit var binding: ActivityGeneratePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_post)

        binding.toolbar.titleName.text = "Make Post"

        Glide.with(this).load(R.drawable.ic_close).into(binding.toolbar.ivBack)
        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }

        /*binding.imageView4.setOnClickListener {
            startActivity(
                Intent(this, CameraActivity::class.java)
                    .putExtra(AppConstant.CAMERA_TYPE, AppConstant.VIDEO)
            )
        }
        binding.imageView3.setOnClickListener {
            startActivity(
                Intent(this, CameraActivity::class.java)
                    .putExtra(AppConstant.CAMERA_TYPE, AppConstant.PHOTO)
            )
        }*/

    }
}