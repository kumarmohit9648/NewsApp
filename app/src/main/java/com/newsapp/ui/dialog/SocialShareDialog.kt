package com.newsapp.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND
import com.newsapp.R
import kotlinx.android.synthetic.main.dialog_social_share.*


class SocialShareDialog(private val activity: Activity, private val shareLink: String) :
    Dialog(activity),
    View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.clearFlags(FLAG_DIM_BEHIND)
        window?.attributes?.windowAnimations = R.style.DialogAnimation

        val wlp: WindowManager.LayoutParams = window?.attributes!!
        wlp.gravity = Gravity.BOTTOM
        wlp.flags = wlp.flags and FLAG_DIM_BEHIND.inv()
        window?.attributes = wlp

        setContentView(R.layout.dialog_social_share)

        ivFacebook.setOnClickListener(this@SocialShareDialog)
        ivTwitter.setOnClickListener(this@SocialShareDialog)
        ivLinkedIn.setOnClickListener(this@SocialShareDialog)
        ivWhatsApp.setOnClickListener(this@SocialShareDialog)
        ivPinterest.setOnClickListener(this@SocialShareDialog)
        ivTumblr.setOnClickListener(this@SocialShareDialog)
    }

    private fun shareLink(directory: String) {
        val isAppInstalled = appInstalledOrNot(directory)

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.`package` = directory
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareLink);

        if (isAppInstalled) {
            context.startActivity(shareIntent)
        } else {
            context.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    private fun appInstalledOrNot(uri: String): Boolean {
        val pm: PackageManager = context.packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivFacebook -> {
                shareLink("com.facebook.katana")
            }
            R.id.ivTwitter -> {
                shareLink("com.twitter.android")
            }
            R.id.ivLinkedIn -> {
                shareLink("com.linkedin.android")
            }
            R.id.ivWhatsApp -> {
                shareLink("com.whatsapp")
            }
            R.id.ivPinterest -> {
                shareLink("com.pinterest")
            }
            R.id.ivTumblr -> {
                shareLink("com.tumblr")
            }
        }
    }
}