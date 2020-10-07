package com.newsapp.ui.activity

import android.os.Bundle
import com.newsapp.R
import com.newsapp.constants.AppConstant
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.fragment.HomeFragment
import com.newsapp.ui.fragment.StateListFragment
import com.newsapp.ui.fragment.TabFragment
import com.newsapp.ui.fragment.TimePassFragment
import kotlinx.android.synthetic.main.toolbar.*

class OpenFragmentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_fragment)

        val title = intent.getStringExtra(AppConstant.FRAGMENT_TITLE)
        titleName.text = title

        ivBack.setOnClickListener {
            finish()
        }

        when (intent.getStringExtra(AppConstant.FRAGMENT_ID)) {
            "fragment_home" -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayout, HomeFragment.newInstance(), null).commit()
            }
            "fragment_state" -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayout, StateListFragment.newInstance(), null).commit()
            }
            "fragment_time_pass" -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayout, TimePassFragment.newInstance(), null).commit()
            }
            "fragment_other" -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayout, TabFragment.newInstance(), null).commit()
            }
        }

    }

}