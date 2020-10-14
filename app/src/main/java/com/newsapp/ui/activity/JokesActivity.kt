package com.newsapp.ui.activity

import android.os.Bundle
import com.newsapp.databinding.ActivityJokesBinding
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.adapter.JokesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_jokes.*

@AndroidEntryPoint
class JokesActivity : BaseActivity() {

    private lateinit var binding: ActivityJokesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.titleName.text = "चुटकुले"

        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }

        recyclerJokes.adapter = JokesAdapter(this, null)

    }
}