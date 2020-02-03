package com.promobileapp.chiasenhac.ui.activity.list

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.adapter.SongAdapter
import com.promobileapp.chiasenhac.base.BaseActivity
import com.promobileapp.chiasenhac.model.Album
import com.promobileapp.chiasenhac.model.Category
import com.promobileapp.chiasenhac.model.Song
import com.promobileapp.chiasenhac.service.MusicPlayerService
import com.promobileapp.chiasenhac.ui.activity.list.presenter.ListListener
import com.promobileapp.chiasenhac.ui.activity.list.presenter.ListPresenter
import com.promobileapp.chiasenhac.ui.activity.player.PlayerActivity
import com.promobileapp.chiasenhac.utils.AppConstants
import com.promobileapp.chiasenhac.widget.VerticalSpacesItemDecoration
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity(), ListListener {
    lateinit var presenter: ListPresenter
    var album: Album? = null
    var category: Category? = null
    lateinit var adapterSong: SongAdapter
    lateinit var listSong: ArrayList<Song>
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
        setContentView(R.layout.activity_list)
        init()
        loadingView.visibility = View.VISIBLE
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
        toolBar.setNavigationOnClickListener {
            onBackPressed()
        }

        adapterSong = SongAdapter(this, null, null, object : SongAdapter.OnSongClickListener {
            override fun onSongSelected(position: Int, song: Song) {
                var intent = Intent(this@ListActivity, PlayerActivity::class.java)
                intent.putExtra("song", song)
                startActivity(intent)
            }
        })

        rv_listSong.setHasFixedSize(true)
        rv_listSong.layoutManager = LinearLayoutManager(this)
        rv_listSong.addItemDecoration(VerticalSpacesItemDecoration(8))
        rv_listSong.adapter = adapterSong

        presenter = ListPresenter(this, this)
        album = intent.getParcelableExtra(AppConstants.ALBUM_INTENT)
        category = intent.getParcelableExtra(AppConstants.CATEGORY_INTENT)

        if (album != null) {
            Glide.with(this).load(album?.thumb).placeholder(R.drawable.thumb_error).into(img_Album)
            tv_title.text = album?.title
            tv_artist.text = album?.artist
            presenter.getListSong(album?.urlAlbum.toString())
        }

        if (category != null) {
            Glide.with(this).load(category?.thumbnail).placeholder(R.drawable.thumb_error).into(img_Album)
            tv_title.text = category?.title
            tv_artist.text = ""
            presenter.getListSong(category?.url.toString())
        }
    }

    override fun onLoadListSongSuccesfull(list: ArrayList<Song>) {
        adapterSong.setData(list)
        loadingView.visibility = View.GONE
    }
}
