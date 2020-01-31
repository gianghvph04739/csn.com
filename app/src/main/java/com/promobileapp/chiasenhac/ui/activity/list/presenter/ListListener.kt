package com.promobileapp.chiasenhac.ui.activity.list.presenter

import com.promobileapp.chiasenhac.model.Song

interface ListListener {
    fun onLoadListSongSuccesfull(list: ArrayList<Song>);
}