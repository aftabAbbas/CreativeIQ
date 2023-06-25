package com.itzcafe.creativeiq.adapters.recycler

import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.itzcafe.creativeiq.R
import com.itzcafe.creativeiq.databinding.ItemPlaylistBinding
import java.util.*

@Suppress("all")
class PlaylistAdapter(
    private var context: Context,
) : RecyclerView.Adapter<PlaylistAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(context)
                .inflate(R.layout.item_playlist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemPlaylistBinding.bind(itemView)
    }
}