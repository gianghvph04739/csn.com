package com.promobileapp.chiasenhac.ui.fragment.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.adapter.SongAdapter
import com.promobileapp.chiasenhac.adapter.iLoadMore
import com.promobileapp.chiasenhac.base.BaseFragment
import com.promobileapp.chiasenhac.model.Song
import com.promobileapp.chiasenhac.ui.fragment.album.presenter.SongListener
import com.promobileapp.chiasenhac.ui.fragment.song.presenter.SongPresenter
import com.promobileapp.chiasenhac.widget.GridSpacesItemDecoration
import kotlinx.android.synthetic.main.fragment_song.*

class SongFragment : BaseFragment(), SongListener, SongAdapter.OnSongClickListener, iLoadMore {
    override fun onSongSelected(position: Int, song: Song) {

    }

    val presenter by lazy {
        SongPresenter(context!!, this)
    }

    var listData: ArrayList<Song> = ArrayList()
    lateinit var songAdapter: SongAdapter
    var pageNumber = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_song, container, false)
        return view
    }

    override fun init() {
        initRvSong()
        presenter.getNewSong()
    }

    fun initRvSong() {
        var layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        songAdapter = SongAdapter(context, rvSong, layoutManager, this)
        songAdapter.setLoadMore(this)
        var space = GridSpacesItemDecoration(8)
        rvSong.addItemDecoration(space)
        rvSong.setHasFixedSize(true)
        rvSong.layoutManager = layoutManager
        rvSong.adapter = songAdapter
    }

    override fun onLoadMore() {
        pageNumber++
        presenter.getMoreNewSong(pageNumber)
        songAdapter.showLoadMore()
    }

    override fun onLoadSongSuccess(list: ArrayList<Song>) {
        songAdapter.setData(list)
    }

    override fun onLoadSongFailed(mess: String) {
    }

    override fun onLoadMoreSongSuccess(list: ArrayList<Song>) {
        listData.clear()
        listData.addAll(list)
        songAdapter.addMoreData(listData)
    }

    override fun onLoadMoreSongFailed(mess: String) {
    }


}
