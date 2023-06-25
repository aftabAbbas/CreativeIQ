package com.itzcafe.creativeiq.adapters.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.itzcafe.creativeiq.ui.fragments.HelpFragment
import com.itzcafe.creativeiq.ui.fragments.HomeFragment
import com.itzcafe.creativeiq.ui.fragments.PlaylistFragment

class HomeTabsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(i: Int): Fragment {
        return when (i) {
            0 -> HomeFragment()
            1 -> PlaylistFragment()
            2 -> HelpFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}