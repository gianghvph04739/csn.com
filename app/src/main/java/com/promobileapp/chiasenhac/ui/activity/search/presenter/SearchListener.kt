package com.promobileapp.chiasenhac.ui.activity.search.presenter

import com.promobileapp.chiasenhac.model.Song

interface SearchListener {
    fun onSearchDone(listData: ArrayList<Song>)
    fun onSearchMore(listMore: ArrayList<Song>)
    fun onSearchFailed()
    fun onSearchMoreFailed()
}