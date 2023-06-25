package com.itzcafe.creativeiq.ui.fragments

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itzcafe.creativeiq.R
import com.itzcafe.creativeiq.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        mainInit()
        return binding.root
    }

    private fun mainInit() {
        clickListeners()
    }

    private fun clickListeners() {
        binding.run {
            nePlayPause.setOnClickListener {
                playMusic()
            }
        }
    }

    private fun playMusic() {
        val afd = requireContext().assets.openFd("headlight.m4a")
        val player = MediaPlayer()
        player.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        player.prepare()
        player.start()
    }
}