package com.itzcafe.creativeiq.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
    }

    private fun setViewPagerAdapter() {
        binding.viewPager.offscreenPageLimit = 3
        tabsAdapter = HomeTabsAdapter(supportFragmentManager)
        binding.viewPager.adapter = tabsAdapter
    }
}