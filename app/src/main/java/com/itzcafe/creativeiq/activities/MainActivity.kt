package com.itzcafe.creativeiq.activities

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.itzcafe.creativeiq.R
import com.itzcafe.creativeiq.adapters.NewsFeedAdapter
import com.itzcafe.creativeiq.databinding.ActivityMainBinding
import com.itzcafe.creativeiq.models.Music
import com.itzcafe.creativeiq.utils.Functions
import java.lang.reflect.Field
import java.util.Timer
import java.util.TimerTask


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
        getIntentValues()
        Functions.hideSystemUI(context)
        fieldList.addAll(Functions.getAllDataFromRaw())
        clickListeners()
        setNewsFeedAdapter()
    }

    private fun getIntentValues() {
        val any = Functions.getIntentData(context, Gson(), Music::class.java)

        if (any != null) {
            val music = any as Music
            playMusicFromRaw(music.rawId)

            binding.run {
                tvSongName.text = music.musicTitle
                tvSongName.isSelected = true
                ivPlayPause.setImageResource(R.drawable.pause)
                tvDurationTime.text = Functions.getMusicDuration(mediaPlayer?.duration!!)
            }
        }
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

            sbPlaying.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if (fromUser && mediaPlayer != null) {
                        mediaPlayer!!.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    mediaPlayer!!.pause()
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    mediaPlayer!!.start()
                }
            })
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
            }
        }
    }

    private fun setProgressToSeekbar() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (mediaPlayer != null && Functions.isMusicPlaying(context)) {
                    runOnUiThread {
                        val currentPosition = mediaPlayer!!.currentPosition
                        binding.sbPlaying.progress = currentPosition
                        val formattedDuration = Functions.formatDuration(currentPosition)
                        binding.tvCurrentTime.text = formattedDuration
                    }
                }
            }
        }, 0, 100)
    }

    private fun playMusicFromRaw(rawId: Int) {
        mediaPlayer?.release()
        mediaPlayer = null

        mediaPlayer = MediaPlayer.create(context, rawId)
        mediaPlayer?.setOnCompletionListener {
            playMusic()
        }

        mediaPlayer?.start()

        binding.run {
            tvDurationTime.text = Functions.getMusicDuration(mediaPlayer?.duration!!)
            sbPlaying.max = mediaPlayer?.duration!!
            setProgressToSeekbar()
        }
    }
}