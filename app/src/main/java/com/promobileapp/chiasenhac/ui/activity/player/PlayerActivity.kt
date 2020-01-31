package com.promobileapp.chiasenhac.ui.activity.player

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.base.BaseActivity
import com.promobileapp.chiasenhac.model.Song
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : BaseActivity(), PareSongListener {
    lateinit var presenterPare: PareSongPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor(Color.WHITE, true)
        setContentView(R.layout.activity_player)
        init()
    }

    override fun init() {
        val song: Song = intent.getParcelableExtra("song")
        presenterPare = PareSongPresenter(this, this)
        presenterPare.getListQualitySong(song.urlSong)
        Glide.with(this).load(song.thumb).into(imgAvt)
        tvTitle.setText(song.title)
        tvArtist.setText(song.artist)
    }

    override fun onLoadListSongSuccesfull() {

    }
}
