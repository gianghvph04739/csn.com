package com.promobileapp.chiasenhac.ui.activity.player

import android.content.ComponentName
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import com.bumptech.glide.Glide
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.base.BaseActivity
import com.promobileapp.chiasenhac.model.Quality
import com.promobileapp.chiasenhac.model.Song
import com.promobileapp.chiasenhac.service.MusicPlayerService
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : BaseActivity(), PareSongListener {
    lateinit var presenterPare: PareSongPresenter
    var mBound = false
    var musicPlayerService: MusicPlayerService? = null
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder: MusicPlayerService.MusicServiceBinder = service as MusicPlayerService.MusicServiceBinder
            musicPlayerService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeStatusBarColor(Color.WHITE, true)
        setContentView(R.layout.activity_player)
        init()
    }

    override fun onResume() {
        super.onResume()
        bindService(connection)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindServicePlayMusic(connection, mBound)
    }

    override fun init() {
        val song: Song = intent.getParcelableExtra("song")
        presenterPare = PareSongPresenter(this, this)
        presenterPare.getListQualitySong(song)
        Glide.with(this).load(song.thumb).into(imgAvt)
        tvTitle.setText(song.title)
        tvArtist.setText(song.artist)
    }

    override fun onLoadListQualitySuccesfull(listQuality: ArrayList<Quality>) {
        if(listQuality.size>=3){
            listQuality.get(2).url?.let { playMusic(it) }
        } else{
            listQuality.get(0).url?.let { playMusic(it) }
        }
    }

    override fun onLoadListqualityError() {

    }

}
