package com.promobileapp.chiasenhac.ui.fragment.home.presenter

import com.promobileapp.chiasenhac.model.Album

interface HomeListener {
    fun onLoadBannerSuccessFull(listBanner: ArrayList<Album>)

    fun onLoadNewAlbumSuccessFull(listNewAlbum: ArrayList<Album>)
}