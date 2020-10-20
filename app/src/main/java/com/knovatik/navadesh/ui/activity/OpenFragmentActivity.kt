package com.knovatik.navadesh.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.knovatik.navadesh.R
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.ui.BaseActivity
import com.knovatik.navadesh.ui.fragment.SubCategoryFragment
import com.knovatik.navadesh.ui.fragment.TabFragment
import com.knovatik.navadesh.ui.fragment.TimePassFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class OpenFragmentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_fragment)

        val title = intent.getStringExtra(AppConstant.FRAGMENT_TITLE)
        titleName.text = title

        var categoryId = intent.getStringExtra(AppConstant.CATEGORY_ID)
        if (categoryId.isNullOrEmpty())
            categoryId = ""

        var fragment: Fragment? = null
        val bundle = Bundle()
        bundle.putString(AppConstant.CATEGORY_ID, categoryId)
        when (intent.getStringExtra(AppConstant.FRAGMENT_ID)) {
            AppConstant.FRAGMENT_SUB_CATEGORY -> {
                fragment = SubCategoryFragment.newInstance()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayout, fragment, null).commit()
            }
            AppConstant.FRAGMENT_TIME_PASS -> {
                fragment = TimePassFragment.newInstance()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayout, fragment, null).commit()
            }
            AppConstant.FRAGMENT_OTHER -> {
                fragment = TabFragment.newInstance()
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .add(R.id.frameLayout, fragment, null).commit()
            }
        }

        ivBack.setOnClickListener {
            finish()
        }

    }

}