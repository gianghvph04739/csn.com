package com.promobileapp.chiasenhac.ui.fragment.album

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.adapter.AlbumAdapter
import com.promobileapp.chiasenhac.adapter.iLoadMore
import com.promobileapp.chiasenhac.base.BaseFragment
import com.promobileapp.chiasenhac.model.Album
import com.promobileapp.chiasenhac.ui.activity.list.ListActivity
import com.promobileapp.chiasenhac.ui.fragment.album.presenter.AlbumListener
import com.promobileapp.chiasenhac.ui.fragment.album.presenter.AlbumPresenter
import com.promobileapp.chiasenhac.utils.AppConstants
import com.promobileapp.chiasenhac.widget.GridSpacesItemDecoration
import kotlinx.android.synthetic.main.fragment_album.*

class AlbumFragment : BaseFragment(), AlbumListener, iLoadMore {
    val presenter by lazy {
        AlbumPresenter(context!!, this)
    }
    var lstALbum: ArrayList<Album> = ArrayList()
    lateinit var albumAdapter: AlbumAdapter
    var pageNumber = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_album, container, false)
        return view
    }

    override fun init() {
        initRvAlbum()
        presenter.getNewAlbum()
    }

    fun initRvAlbum() {
        var gridLayoutManager = GridLayoutManager(context, 2)
        albumAdapter = AlbumAdapter(context!!, rvAlbum, gridLayoutManager, onClickAlbum = { album ->
            val intent = Intent(context, ListActivity::class.java)
            intent.putExtra(AppConstants.ALBUM_INTENT, album)
            startActivity(intent)
        })
        albumAdapter.setLoadMore(this)
        var space = GridSpacesItemDecoration(8)
        rvAlbum.addItemDecoration(space)
        rvAlbum.setHasFixedSize(true)
        rvAlbum.layoutManager = gridLayoutManager
        rvAlbum.adapter = albumAdapter
    }

    override fun onLoadAlbumSuccess(list: ArrayList<Album>) {
        albumAdapter.setData(list)
    }

    override fun onLoadMore() {
        pageNumber++
        presenter.getMoreNewAlbum(pageNumber)
        albumAdapter.showLoadMore()
    }

    override fun onLoadAlbumFailed(mess: String) {
    }

    override fun onLoadMoreAlbumSuccess(list: ArrayList<Album>) {
        lstALbum.clear()
        lstALbum.addAll(list)
        albumAdapter.addMoreData(lstALbum)
    }

    override fun onLoadMoreAlbumFailed(mess: String) {
    }
}
