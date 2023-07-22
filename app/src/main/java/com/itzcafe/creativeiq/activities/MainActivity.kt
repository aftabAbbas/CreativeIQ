package com.itzcafe.creativeiq.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.itzcafe.creativeiq.R
import com.itzcafe.creativeiq.adapters.HomeTabsAdapter
import com.itzcafe.creativeiq.databinding.ActivityMainBinding
import com.itzcafe.creativeiq.utils.Functions

class MainActivity : AppCompatActivity() {

    private var context = this
    private lateinit var binding: ActivityMainBinding
    private lateinit var tabsAdapter: HomeTabsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainInit()
    }

    private fun mainInit() {
        Functions.disableDarkMode()
        Functions.hideStatusBar(context)
        setViewPagerAdapter()
        setBottomNavigation()
    }

    private fun setViewPagerAdapter() {
        binding.viewPager.offscreenPageLimit = 3
        tabsAdapter = HomeTabsAdapter(supportFragmentManager)
        binding.viewPager.adapter = tabsAdapter
    }

    private fun setBottomNavigation() {
        binding.run {
            viewPager.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            bottomNavigationView.menu.findItem(R.id.menu_home).isChecked = true
                            viewPager.currentItem = 0
                        }

                        1 -> {
                            bottomNavigationView.menu.findItem(R.id.menu_news_feed).isChecked = true
                            viewPager.currentItem = 1
                        }

                        2 -> {
                            bottomNavigationView.menu.findItem(R.id.menu_settings).isChecked = true
                            viewPager.currentItem = 2
                        }
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })

            bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.menu_home -> {
                        bottomNavigationView.menu.findItem(R.id.menu_home).isChecked = true
                        viewPager.currentItem = 0
                    }

                    R.id.menu_news_feed -> {
                        bottomNavigationView.menu.findItem(R.id.menu_news_feed).isChecked = true
                        viewPager.currentItem = 1
                    }

                    R.id.menu_settings -> {
                        bottomNavigationView.menu.findItem(R.id.menu_settings).isChecked = true
                        viewPager.currentItem = 2
                    }
                }
                false
            }
        }
    }
}