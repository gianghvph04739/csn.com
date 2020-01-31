package com.promobileapp.chiasenhac.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.promobileapp.chiasenhac.adapter.viewholder.SongChartViewHolder
import com.promobileapp.chiasenhac.model.Song
import com.promobileapp.chiasenhac.ui.activity.player.PlayerActivity

class SongChartAdapter(
    private val context: Context?,
    private val mListener: SongAdapter.OnSongClickListener
) :
    RecyclerView.Adapter<SongChartViewHolder>() {
    var lstData: ArrayList<Song> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongChartViewHolder {
        val inflate = LayoutInflater.from(context)
        return SongChartViewHolder(context, inflate, parent)
    }

    override fun getItemCount(): Int {
        return lstData.size
    }

    override fun onBindViewHolder(holder: SongChartViewHolder, position: Int) {
        var song = lstData.get(position)
        holder.bind(song, position + 1)
        holder.root?.setOnClickListener{
            mListener.onSongSelected(position,song)
        }
    }

    fun setData(listData: ArrayList<Song>) {
        lstData.clear()
        lstData.addAll(listData)
        notifyDataSetChanged()
    }
}