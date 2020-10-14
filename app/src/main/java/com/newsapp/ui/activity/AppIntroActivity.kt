package com.newsapp.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType
import com.newsapp.R
import com.newsapp.constants.AppConstant
import com.pixplicity.easyprefs.library.Prefs

class AppIntroActivity : AppIntro() {

    private var flag = 0
    private val permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    flag = 1
                    showIntroSlides()
                    break
                }
            }
        }

        if (flag == 0) {
            if (Prefs.getBoolean(AppConstant.IS_FIRST_TIME_OPEN, false))
                goToDashboard()
            else {
                Prefs.putBoolean(AppConstant.IS_FIRST_TIME_OPEN, true)
                showIntroSlides()
            }
        }

    }

    private fun showIntroSlides() {

        addSlide(
            AppIntroFragment.newInstance(
                title = "Welcome!",
                description = "We will make you updated with news",
                imageDrawable = R.drawable.app_logo,
                backgroundColor = ContextCompat.getColor(this@AppIntroActivity, R.color.orange)
            )
        )

        addSlide(
            AppIntroFragment.newInstance(
                title = "Permission Request",
                description = "In order to access your location, you must give permission.",
                imageDrawable = R.drawable.ic_intro_location,
                backgroundColor = ContextCompat.getColor(
                    this@AppIntroActivity,
                    R.color.orange
                )
            )
        )

        addSlide(
            AppIntroFragment.newInstance(
                title = "Permission Request",
                description = "In order to access your camera, you must give permission.",
                imageDrawable = R.drawable.ic_intro_video,
                backgroundColor = ContextCompat.getColor(
                    this@AppIntroActivity,
                    R.color.orange
                )
            )
        )

        addSlide(
            AppIntroFragment.newInstance(
                title = "Permission Request",
                description = "In order to access your audio, you must give permissions.",
                imageDrawable = R.drawable.ic_intro_audio,
                backgroundColor = ContextCompat.getColor(
                    this@AppIntroActivity,
                    R.color.orange
                )
            )
        )

        addSlide(
            AppIntroFragment.newInstance(
                title = "Permission Request",
                description = "In order to access your storage, you must give permissions.",
                imageDrawable = R.drawable.ic_intro_storage,
                backgroundColor = ContextCompat.getColor(
                    this@AppIntroActivity,
                    R.color.orange
                )
            )
        )

        isSkipButtonEnabled = false

        // Here we ask for camera permission on slide 2
        askForPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2)
        askForPermissions(arrayOf(Manifest.permission.CAMERA), 3)
        askForPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 4)
        askForPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 5)

        setTransformer(
            AppIntroPageTransformerType.Parallax(
                titleParallaxFactor = 1.0,
                imageParallaxFactor = -1.0,
                descriptionParallaxFactor = 2.0
            )
        )

        isIndicatorEnabled = true

    }

    private fun goToDashboard() {
        startActivity(Intent(this@AppIntroActivity, LoginOptionActivity::class.java))
        finishAffinity()
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        goToDashboard()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        goToDashboard()
    }
}