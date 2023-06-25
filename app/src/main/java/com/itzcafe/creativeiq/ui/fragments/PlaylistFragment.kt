package com.itzcafe.creativeiq.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itzcafe.creativeiq.R
import com.itzcafe.creativeiq.adapters.recycler.PlaylistAdapter
import com.itzcafe.creativeiq.databinding.FragmentHomeBinding
import com.itzcafe.creativeiq.databinding.FragmentPlaylistBinding

class PlaylistFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistBinding.inflate(layoutInflater, container, false)
        mainInit()
        return binding.root
    }

    private fun mainInit() {
        setPlaylistAdapter()
    }

    private fun setPlaylistAdapter() {
        val adapter = PlaylistAdapter(requireContext())
        binding.rvPlaylist.adapter = adapter
    }
}