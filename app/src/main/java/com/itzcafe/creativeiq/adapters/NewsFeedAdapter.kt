package com.itzcafe.creativeiq.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itzcafe.creativeiq.R
import com.itzcafe.creativeiq.databinding.ItemPlaylistBinding
import java.lang.reflect.Field
import java.util.*

@Suppress("all")
class NewsFeedAdapter(
    private var context: Context
) : RecyclerView.Adapter<NewsFeedAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(context)
                .inflate(R.layout.item_playlist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.run {

        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemPlaylistBinding.bind(itemView)
    }
}