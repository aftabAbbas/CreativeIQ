package com.itzcafe.creativeiq.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.itzcafe.creativeiq.R
import com.itzcafe.creativeiq.activities.MainActivity
import com.itzcafe.creativeiq.databinding.ItemPlaylistBinding
import com.itzcafe.creativeiq.models.Music
import com.itzcafe.creativeiq.utils.Functions
import java.lang.reflect.Field
import java.util.*

@Suppress("all")
class PlaylistAdapter(
    private var context: Context,
    private var arrayList: ArrayList<Field>
) : RecyclerView.Adapter<PlaylistAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(context)
                .inflate(R.layout.item_playlist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.run {
            tvTitle.text = arrayList[position].name
        }

        holder.itemView.setOnClickListener {
            val rawId = arrayList[holder.adapterPosition].getInt(null)
            val music = Music(rawId, arrayList[holder.adapterPosition].name)

            Functions.startActivityWithFlagsAndData(
                context, MainActivity::class.java, Gson(), music
            )
            (context as Activity).overridePendingTransition(0, 0)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemPlaylistBinding.bind(itemView)
    }
}