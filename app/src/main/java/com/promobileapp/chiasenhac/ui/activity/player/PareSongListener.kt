package com.promobileapp.chiasenhac.ui.activity.player

import com.promobileapp.chiasenhac.model.Quality

interface PareSongListener {
    fun onLoadListQualitySuccesfull(listQuality: ArrayList<Quality>)
    fun onLoadListqualityError()
}