package com.itzcafe.creativeiq.ui.fragments

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.itzcafe.creativeiq.R
import com.itzcafe.creativeiq.databinding.FragmentHomeBinding
import com.itzcafe.creativeiq.utils.Functions
import me.tankery.lib.circularseekbar.CircularSeekBar
import me.tankery.lib.circularseekbar.CircularSeekBar.OnCircularSeekBarChangeListener
import java.util.Timer
import java.util.TimerTask
import kotlin.math.roundToInt


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var currentIndex = 0
    private var mediaPlayer: MediaPlayer? = null

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
                if (!Functions.isMusicPlaying(requireContext())) {
                    playMusic()
                    val timer = Timer()
                    timer.scheduleAtFixedRate(object : TimerTask() {
                        override fun run() {
                            currentlyPlayingDuration.progress = mediaPlayer!!.currentPosition
                        }
                    }, 0, 1000)

                    nePlayPause.setImageResource(R.drawable.pause)
                } else {
                    nePlayPause.setImageResource(R.drawable.play)
                    mediaPlayer?.stop()
                }
            }

            cvSeekbarVolume.setOnSeekBarChangeListener(object : OnCircularSeekBarChangeListener {
                override fun onProgressChanged(
                    circularSeekBar: CircularSeekBar, progress: Float, fromUser: Boolean
                ) {
                    val voloum = progress.roundToInt()
                    val audioManager =
                        requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, voloum, 0)
                }

                override fun onStopTrackingTouch(seekBar: CircularSeekBar) {}
                override fun onStartTrackingTouch(seekBar: CircularSeekBar) {}
            })
        }
    }

    private fun playMusic() {
        if (currentIndex < Functions.getAllDataFromRaw().size) {
            val field = Functions.getAllDataFromRaw()[currentIndex]
            val rawId = field.getInt(null)
            playMusicFromRaw(rawId)
            currentIndex++
        }
    }

    private fun playMusicFromRaw(rawId: Int) {
        mediaPlayer?.release()
        mediaPlayer = null

        mediaPlayer = MediaPlayer.create(requireContext(), rawId)
        mediaPlayer?.setOnCompletionListener {
            playMusic()
        }

        mediaPlayer?.start()
    }
}