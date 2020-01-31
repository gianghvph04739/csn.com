package com.promobileapp.chiasenhac.ui.activity.category.presenter

import com.promobileapp.chiasenhac.model.Album

interface CategoryDetailListener {
    fun onLoadListDetailDone(listAlbum: ArrayList<Album>)
    fun onLoadListDetailFaied()
}