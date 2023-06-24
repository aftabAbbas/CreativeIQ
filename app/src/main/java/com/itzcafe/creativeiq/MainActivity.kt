package com.itzcafe.creativeiq

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itzcafe.creativeiq.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var context = this
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val afd = assets.openFd("headlight.m4a")
        val player = MediaPlayer()
        player.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        player.prepare()
        player.start()
    }
}