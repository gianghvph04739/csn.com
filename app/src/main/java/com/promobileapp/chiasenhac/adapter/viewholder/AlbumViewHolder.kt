package com.promobileapp.chiasenhac.adapter.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.model.Album

class AlbumViewHolder(private val context: Context, inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_album_card, parent, false)) {
    private var imgThumb: ImageView? = null
    private var tvTitle: TextView? = null
    private var tvArtist: TextView? = null
    private var tvQuality: TextView? = null

    init {
        imgThumb = itemView.findViewById(R.id.imgThumb)
        tvTitle = itemView.findViewById(R.id.tv_title)
        tvArtist = itemView.findViewById(R.id.tv_artist)
        tvQuality = itemView.findViewById(R.id.tv_quality)
    }

    fun bind(album: Album) {
        tvTitle!!.text = album.title
        tvArtist!!.text = album.artist
        tvQuality!!.text = album.quality
        Glide.with(context)
            .load(album.thumb)
            .placeholder(R.drawable.thumb_error)
            .into(imgThumb!!)
    }
}