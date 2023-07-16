package com.itzcafe.creativeiq.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.itzcafe.creativeiq.fragments.MusicFragment
import com.itzcafe.creativeiq.fragments.NewsFeedFragment
import com.itzcafe.creativeiq.fragments.HelpFragment

class HomeTabsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(i: Int): Fragment {
        return when (i) {
            0 -> MusicFragment()
            1 -> NewsFeedFragment()
            2 -> HelpFragment()
            else -> MusicFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}