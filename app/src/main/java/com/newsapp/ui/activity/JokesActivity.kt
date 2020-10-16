package com.newsapp.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.ActivityJokesBinding
import com.newsapp.model.section.SectionItemRequest
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.adapter.JokesAdapter
import com.newsapp.ui.vm.JokesViewModel
import com.newsapp.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_jokes.*

@AndroidEntryPoint
class JokesActivity : BaseActivity() {

    private lateinit var binding: ActivityJokesBinding
    private val viewModel: JokesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.titleName.text = "चुटकुले"

        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }

        setData()

        viewModel.getSectionItemResponse.observe(this, {
            if (it.status) {
                if (it.data != null) {
                    recyclerJokes.adapter = JokesAdapter(this, it.data)
                }
            }
            toast(it.message)
        })

    }

    private fun setData() {
        viewModel.getSectionItem(
            SectionItemRequest(
                Prefs.getString(AppConstant.AUTH_TOKEN, ""),
                "1"
            )
        )
    }
}