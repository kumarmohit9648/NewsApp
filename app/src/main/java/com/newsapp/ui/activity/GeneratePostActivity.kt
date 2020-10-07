package com.newsapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.newsapp.R
import kotlinx.android.synthetic.main.toolbar.*

class GeneratePostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_post)

        titleName.text = "Make Post"

        ivBack.setOnClickListener {
            finish()
        }

    }
}