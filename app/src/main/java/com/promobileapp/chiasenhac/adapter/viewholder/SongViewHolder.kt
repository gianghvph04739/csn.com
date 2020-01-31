package com.promobileapp.chiasenhac.adapter.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.model.Song

class SongViewHolder(private val context: Context, inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_song, parent, false)) {
    private var imgThumb: ImageView? = null
    private var tvTitle: TextView? = null
    private var tvArtist: TextView? = null
    private var tvQuality: TextView? = null
    var root: ConstraintLayout? = null

    init {
        imgThumb = itemView.findViewById(R.id.imgThumb)
        tvTitle = itemView.findViewById(R.id.tv_title)
        tvArtist = itemView.findViewById(R.id.tv_artist)
        tvQuality = itemView.findViewById(R.id.tv_quality)
        root = itemView.findViewById(R.id.root)
    }

    fun bind(song: Song) {
        tvTitle!!.text = song.title
        tvArtist!!.text = song.artist
        tvQuality!!.text = song.quality
        Glide.with(context).load(song.thumb).into(imgThumb!!)
    }
}