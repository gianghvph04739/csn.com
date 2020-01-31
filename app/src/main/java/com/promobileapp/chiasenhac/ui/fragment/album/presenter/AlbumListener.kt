package com.promobileapp.chiasenhac.ui.fragment.album.presenter

import com.promobileapp.chiasenhac.model.Album

interface AlbumListener {
    fun onLoadAlbumSuccess(list: ArrayList<Album>)

    fun onLoadAlbumFailed(mess: String)

    fun onLoadMoreAlbumSuccess(list: ArrayList<Album>)

    fun onLoadMoreAlbumFailed(mess: String)
}