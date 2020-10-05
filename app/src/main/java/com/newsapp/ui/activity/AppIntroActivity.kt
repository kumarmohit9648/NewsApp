package com.newsapp.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType
import com.newsapp.R
import com.newsapp.constants.AppConstant
import com.pixplicity.easyprefs.library.Prefs

class AppIntroActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_app_intro)

        if (Prefs.getBoolean(AppConstant.IS_FIRST_TIME_OPEN, false))
            goToDashboard()
        else
            showIntroSlides()

    }

    private fun showIntroSlides() {
        Prefs.putBoolean(AppConstant.IS_FIRST_TIME_OPEN, true)

        addSlide(
            AppIntroFragment.newInstance(
                "Welcome!",
                "We will make you updated with news",
                imageDrawable = R.drawable.app_logo
            )
        )

        addSlide(
            AppIntroFragment.newInstance(
                "Permission Request",
                "In order to access your location, you must give permissions.",
                imageDrawable = R.drawable.ic_pin
            )
        )

        // Here we ask for camera permission on slide 2
        askForPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2)

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
        startActivity(Intent(this@AppIntroActivity, DashboardActivity::class.java))
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