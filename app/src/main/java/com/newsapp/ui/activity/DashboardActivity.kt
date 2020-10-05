package com.newsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.newsapp.R
import com.newsapp.constants.AppConstant
import com.newsapp.helper.ZoomOutPageTransformer
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.fragment.TabFragment
import com.newsapp.ui.vm.DashboardViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.layout_dashboard.*
import kotlinx.android.synthetic.main.navigation_drawer_dashboard.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.util.*

class DashboardActivity : BaseActivity(), KodeinAware {

    override val kodein by kodein()
    private lateinit var viewModel: DashboardViewModel
    // private val factory: DashboardViewModelFactory by instance<DashboardViewModelFactory>()

    private val fragmentName =
        arrayOf(
            "होम",
            "भारत",
            "चुनाव",
            "मनोरंजन",
            "कोरोना",
            "जुर्म",
            "ट्रेडिंग"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setUpDrawer()
        setUpViewPager(viewpager)
        clickListener()

        tabs.setupWithViewPager(viewpager)
    }

    private fun clickListener() {

        search.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, SearchActivity::class.java))
        }

        notification.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, SettingActivity::class.java))
        }

        newsNew.setOnClickListener {
            startActivity(
                Intent(
                    this@DashboardActivity,
                    NewsListActivity::class.java
                ).putExtra(AppConstant.TITLE_KEY, "ख़बरें")
            )
        }
        newsState.setOnClickListener {
            startActivity(
                Intent(
                    this@DashboardActivity,
                    NewsListActivity::class.java
                ).putExtra(AppConstant.TITLE_KEY, "राज्य")
            )
        }
        newsSport.setOnClickListener {
            startActivity(
                Intent(
                    this@DashboardActivity,
                    NewsListActivity::class.java
                ).putExtra(AppConstant.TITLE_KEY, "खेल")
            )
        }
        newsEntertainment.setOnClickListener {
            startActivity(
                Intent(
                    this@DashboardActivity,
                    NewsListActivity::class.java
                ).putExtra(AppConstant.TITLE_KEY, "मनोरंजन")
            )
        }
        newsPolitics.setOnClickListener {
            startActivity(
                Intent(
                    this@DashboardActivity,
                    NewsListActivity::class.java
                ).putExtra(AppConstant.TITLE_KEY, "राजनीति")
            )
        }
        newsLifestyle.setOnClickListener {
            startActivity(
                Intent(
                    this@DashboardActivity,
                    NewsListActivity::class.java
                ).putExtra(AppConstant.TITLE_KEY, "लाइफस्टाइल")
            )
        }
        newsCareer.setOnClickListener {
            startActivity(
                Intent(
                    this@DashboardActivity,
                    NewsListActivity::class.java
                ).putExtra(AppConstant.TITLE_KEY, "करियर")
            )
        }
        newsAnalysis.setOnClickListener {
            startActivity(
                Intent(
                    this@DashboardActivity,
                    NewsListActivity::class.java
                ).putExtra(AppConstant.TITLE_KEY, "विश्लेषण")
            )
        }

    }

    private fun setUpViewPager(viewpager: ViewPager) {
        viewpager.setPageTransformer(true, ZoomOutPageTransformer())
        val adapter = ViewPagerAdapter(supportFragmentManager)
        for (i in fragmentName) {
            adapter.addFrag(TabFragment(), i)
        }
        viewpager.adapter = adapter
    }

    private fun setUpDrawer() {
        menu.setImageResource(R.drawable.ic_menu)

        menu.setOnClickListener {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }

            drawerLayout.drawerElevation = 0F

            drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
                override fun onDrawerStateChanged(p0: Int) {
                }

                override fun onDrawerSlide(p0: View, p1: Float) {

                }

                override fun onDrawerClosed(p0: View) {

                }

                override fun onDrawerOpened(p0: View) {
                    // setUpNavItems()
                }
            })
        }

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            finish()
        }
    }

    class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(
            fragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {

        private val fragmentList = ArrayList<Fragment>()
        private val fragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList[position]
        }

    }
}