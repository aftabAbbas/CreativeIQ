package com.itzcafe.creativeiq.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.itzcafe.creativeiq.adapters.recycler.PlaylistAdapter
import com.itzcafe.creativeiq.databinding.FragmentPlaylistBinding
import com.itzcafe.creativeiq.utils.Functions

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
        val adapter = PlaylistAdapter(requireContext(), Functions.getAllDataFromRaw())
        binding.rvPlaylist.adapter = adapter
    }
}