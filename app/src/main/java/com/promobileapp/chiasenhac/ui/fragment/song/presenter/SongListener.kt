package com.promobileapp.chiasenhac.ui.fragment.album.presenter

import com.promobileapp.chiasenhac.model.Song

interface SongListener {
    fun onLoadSongSuccess(list: ArrayList<Song>)

    fun onLoadSongFailed(mess: String)

    fun onLoadMoreSongSuccess(list: ArrayList<Song>)

    fun onLoadMoreSongFailed(mess: String)
}