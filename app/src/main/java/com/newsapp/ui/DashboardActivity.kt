package com.newsapp.ui

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
import com.newsapp.ui.fragment.TabFragment
import com.newsapp.ui.vm.DashboardViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.layout_dashboard.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.util.*

class DashboardActivity : BaseActivity(), KodeinAware {

    override val kodein by kodein()
    private lateinit var viewModel: DashboardViewModel
    // private val factory: DashboardViewModelFactory by instance<DashboardViewModelFactory>()

    private val fragmentName =
        arrayOf(
            "New",
            "Breaking",
            "Video",
            "Trending",
            "Viral"
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

        /*add_news.setOnClickListener {
            startActivity(Intent(this, FilterActivity::class.java))
        }*/
    }

    private fun setUpViewPager(viewpager: ViewPager) {
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