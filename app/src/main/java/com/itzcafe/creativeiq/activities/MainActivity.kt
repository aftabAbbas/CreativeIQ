package com.itzcafe.creativeiq.activities

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itzcafe.creativeiq.R
import com.itzcafe.creativeiq.adapters.NewsFeedAdapter
import com.itzcafe.creativeiq.databinding.ActivityMainBinding
import com.itzcafe.creativeiq.utils.Functions
import java.lang.reflect.Field

class MainActivity : AppCompatActivity() {

    private var context = this
    private lateinit var binding: ActivityMainBinding
    private var currentIndex = 0
    private var mediaPlayer: MediaPlayer? = null
    private var fieldList: ArrayList<Field> = ArrayList()
    private var isMusicPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainInit()
    }

    private fun mainInit() {
        fieldList.addAll(Functions.getAllDataFromRaw())
        clickListeners()
        setNewsFeedAdapter()
    }

    private fun setNewsFeedAdapter() {
        val adapter = NewsFeedAdapter(context)
        binding.rvNewsFeed.adapter = adapter
    }

    private fun clickListeners() {
        binding.run {
            ivPlayPause.setOnClickListener {
                if (!isMusicPaused) {
                    if (!Functions.isMusicPlaying(context)) {
                        playMusic()
                        ivPlayPause.setImageResource(R.drawable.pause)

                    } else {
                        ivPlayPause.setImageResource(R.drawable.play)
                        mediaPlayer?.pause()
                        isMusicPaused = true
                    }

                } else {
                    mediaPlayer?.start()
                    ivPlayPause.setImageResource(R.drawable.pause)
                    isMusicPaused = false
                }
            }

            ivNext.setOnClickListener {
                if (fieldList.size > currentIndex + 1) {
                    currentIndex++
                    playMusic()
                } else {
                    currentIndex = 0
                    playMusic()
                }
            }

            ivPrevious.setOnClickListener {
                if (fieldList.size > currentIndex) {
                    if (currentIndex == 0) {
                        currentIndex = fieldList.size - 1
                    } else {
                        currentIndex--
                    }

                    playMusic()
                }
            }

            ivList.setOnClickListener {
                Functions.startActivity(context, MyPlaylistActivity::class.java)
            }
        }
    }

    private fun playMusic() {
        if (currentIndex < fieldList.size) {
            val field = Functions.getAllDataFromRaw()[currentIndex]
            val rawId = field.getInt(null)
            playMusicFromRaw(rawId)

            binding.run {
                tvSongName.text = field.name
                tvSongName.isSelected = true
                tvDurationTime.text = Functions.getMusicDuration(mediaPlayer?.duration!!)
            }
        }
    }

    private fun playMusicFromRaw(rawId: Int) {
        mediaPlayer?.release()
        mediaPlayer = null

        mediaPlayer = MediaPlayer.create(context, rawId)
        mediaPlayer?.setOnCompletionListener {
            playMusic()
        }

        mediaPlayer?.start()
    }
}