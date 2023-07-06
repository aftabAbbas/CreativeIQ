package com.itzcafe.creativeiq.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.itzcafe.creativeiq.R
import com.itzcafe.creativeiq.adapters.NewsFeedAdapter
import com.itzcafe.creativeiq.adapters.PlaylistAdapter
import com.itzcafe.creativeiq.databinding.ActivityMainBinding
import com.itzcafe.creativeiq.databinding.BottomSheetBinding
import com.itzcafe.creativeiq.interfaces.GetMusic
import com.itzcafe.creativeiq.models.Music
import com.itzcafe.creativeiq.utils.Functions
import com.itzcafe.creativeiq.utils.SharedPref
import java.lang.reflect.Field
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity(), GetMusic {

    private var context = this
    private lateinit var binding: ActivityMainBinding
    private var currentIndex = 0
    private var fieldList: ArrayList<Field> = ArrayList()
    private var isMusicPaused = false
    private var isMusicEnd = false
    private var isPlayingSingleMusic = false
    private lateinit var sp: SharedPref
    private lateinit var dialog: BottomSheetDialog

    companion object {
        var mediaPlayer: MediaPlayer? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainInit()
    }

    private fun mainInit() {
        sp = SharedPref(context)
        Functions.disableDarkMode()
        Functions.hideStatusBar(context)
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

                    } else {
                        ivPlayPause.setImageResource(R.drawable.play)
                        mediaPlayer?.pause()
                        isMusicPaused = true
                    }

                } else {
                    if (!isMusicEnd) {
                        mediaPlayer?.start()
                        ivPlayPause.setImageResource(R.drawable.pause)
                        isMusicPaused = false
                    } else {
                        playMusic()
                    }
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
                showBottomSheet()
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

            ivReplay.setOnClickListener {
                if (!isPlayingSingleMusic) {
                    isPlayingSingleMusic = true
                    ivReplay.setImageResource(R.drawable.single_music)
                } else {
                    isPlayingSingleMusic = false
                    ivReplay.setImageResource(R.drawable.loop)
                }
            }
        }
    }

    private fun playMusic() {
        isMusicPaused = false

        if (currentIndex < fieldList.size) {
            val field = Functions.getAllDataFromRaw()[currentIndex]
            val rawId = field.getInt(null)
            playMusicFromRaw(rawId)

            binding.run {
                tvSongName.text = field.name
                tvSongName.isSelected = true
                ivPlayPause.setImageResource(R.drawable.pause)
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
        sp.save("currentlyPlayingSong", currentIndex)

        mediaPlayer = MediaPlayer.create(context, rawId)
        mediaPlayer?.setOnCompletionListener {
            if (!isPlayingSingleMusic) {
                mediaPlayer!!.stop()
                binding.ivPlayPause.setImageResource(R.drawable.play)
                isMusicPaused = true
                isMusicEnd = true
            } else {
                playMusic()
            }
        }

        mediaPlayer?.start()

        binding.run {
            tvDurationTime.text = Functions.getMusicDuration(mediaPlayer?.duration!!)
            sbPlaying.max = mediaPlayer?.duration!!
            setProgressToSeekbar()
        }
    }

    private fun showBottomSheet() {
        dialog = BottomSheetDialog(context, R.style.customBottomSheetStyle)
        val bottomSheetBinding = BottomSheetBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(bottomSheetBinding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        setPlaylistAdapter(bottomSheetBinding)
    }

    private fun setPlaylistAdapter(bottomSheetBinding: BottomSheetBinding) {
        val adapter = PlaylistAdapter(context, Functions.getAllDataFromRaw(), this)
        bottomSheetBinding.rvPlaylist.adapter = adapter
    }

    override fun getMusic(music: Music) {
        currentIndex = music.songIndex
        playMusicFromRaw(music.rawId)

        binding.run {
            tvSongName.text = music.musicTitle
            tvSongName.isSelected = true
            ivPlayPause.setImageResource(R.drawable.pause)
            tvDurationTime.text = Functions.getMusicDuration(mediaPlayer?.duration!!)
        }

        dialog.dismiss()
    }
}