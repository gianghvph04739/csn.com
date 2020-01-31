package com.promobileapp.chiasenhac.adapter.viewholder

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.model.Song

class SongChartViewHolder(
    private val mContext: Context?,
    inflater: LayoutInflater?,
    parent: ViewGroup?
) :
    RecyclerView.ViewHolder(inflater!!.inflate(R.layout.item_song_chart, parent, false)) {

    private var imgThumb: ImageView? = null
    private var tvTitle: TextView? = null
    private var tvArtist: TextView? = null
    private var tvQuality: TextView? = null
    private var tvNumberChart: TextView? = null
    var root: ConstraintLayout? = null

    init {
        imgThumb = itemView.findViewById(R.id.imgThumb)
        tvTitle = itemView.findViewById(R.id.tv_title)
        tvArtist = itemView.findViewById(R.id.tv_artist)
        tvQuality = itemView.findViewById(R.id.tv_quality)
        tvNumberChart = itemView.findViewById(R.id.tv_topNumber)
        root = itemView.findViewById(R.id.rootSong)
    }

    fun bind(song: Song, number: Int) {
        tvTitle?.text = song.title
        tvArtist?.text = song.artist
        tvQuality?.text = song.quality
        Glide.with(mContext!!)
            .load(song.thumb)
            .into(imgThumb!!)

        tvNumberChart?.text = number.toString()
        if (number == 1) {
            tvNumberChart?.textSize = 28F
            tvNumberChart?.setTextColor(Color.RED)
        } else if (number == 2) {
            tvNumberChart?.textSize = 24F
            tvNumberChart?.setTextColor(Color.GREEN)
        } else if (number == 3) {
            tvNumberChart?.textSize = 20F
            tvNumberChart?.setTextColor(Color.YELLOW)
        } else {
            tvNumberChart?.textSize = 16F
            tvNumberChart?.setTextColor(Color.WHITE)
        }
    }
}