package com.newsapp.ui.activity

import android.os.Bundle
import com.newsapp.R
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.adapter.CommentAdapter
import com.newsapp.ui.dialog.SocialShareDialog
import kotlinx.android.synthetic.main.activity_news_detail.*
import kotlinx.android.synthetic.main.toolbar.*

class NewsDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        titleName.text = "न्यूज़ डिटेल्स"

        ivBack.setOnClickListener {
            supportFinishAfterTransition()
        }

        ivShare.setOnClickListener {
            val socialShareDialog = SocialShareDialog(this@NewsDetailActivity)
            socialShareDialog.show()
            /*val animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {

                }

                override fun onAnimationRepeat(animation: Animation?) {

                }

            })
            animation.start()*/
        }

        commentRecycler.adapter = CommentAdapter(this@NewsDetailActivity, null)
    }
}