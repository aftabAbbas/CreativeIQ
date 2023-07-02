package com.itzcafe.creativeiq.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itzcafe.creativeiq.adapters.PlaylistAdapter
import com.itzcafe.creativeiq.databinding.ActivityMyPlaylistBinding
import com.itzcafe.creativeiq.utils.Functions

class MyPlaylistActivity : AppCompatActivity() {

    private var context = this
    private lateinit var binding: ActivityMyPlaylistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainInit()
    }

    private fun mainInit() {
        Functions.hideSystemUI(context)
        setPlaylistAdapter()
        setToolbar()
    }

    private fun setToolbar() {
        binding.run {
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    private fun setPlaylistAdapter() {
        val adapter = PlaylistAdapter(context, Functions.getAllDataFromRaw())
        binding.rvPlaylist.adapter = adapter
    }
}