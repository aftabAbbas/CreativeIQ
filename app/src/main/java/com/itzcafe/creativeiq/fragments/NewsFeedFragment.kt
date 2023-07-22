package com.itzcafe.creativeiq.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itzcafe.creativeiq.R
import com.itzcafe.creativeiq.adapters.NewsFeedAdapter
import com.itzcafe.creativeiq.databinding.FragmentNewsFeedBinding

class NewsFeedFragment : Fragment() {

    private lateinit var binding: FragmentNewsFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsFeedBinding.inflate(layoutInflater, container, false)
        setNewsFeedAdapter()
        return binding.root
    }

    private fun setNewsFeedAdapter() {
        val adapter = NewsFeedAdapter(requireContext())
        binding.rvNewsFeed.adapter = adapter
    }
}