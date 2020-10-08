package com.newsapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.newsapp.R
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

    }
}