package com.promobileapp.chiasenhac.ui.fragment.song.presenter

import android.content.Context
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.model.Song
import com.promobileapp.chiasenhac.net.VolleySingleton
import com.promobileapp.chiasenhac.ui.fragment.album.presenter.SongListener
import com.promobileapp.chiasenhac.utils.AppConstants
import com.promobileapp.chiasenhac.utils.AppUtils
import com.promobileapp.chiasenhac.utils.PareUtils
import org.jsoup.Jsoup

class SongPresenter(private val context: Context, private val listener: SongListener) {

    fun getNewSong() {
        Thread(Runnable {
            var lstSong: ArrayList<Song> = ArrayList()

            var urlSong = "${AppConstants.BASE_URL}${AppConstants.NEW_MUSIC}"
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val stringRequest = object : StringRequest(Request.Method.GET, urlSong,
                Response.Listener { response ->
                    val doc = Jsoup.parse(response)
                    val elmMain = doc.select(".container")
                    val elmx = elmMain.select("#music_news")
                    for (content in elmx.select(".element.mb-2")) {
                        val url = content.attr("href")
                        val elmThumb = content.select(".image100.mr-2.d-inline-block.align-middle")
                        val thumb = PareUtils.subStringNewSong(elmThumb.attr("style").toString())
                        val albumName =
                            content.select(".name_song.text-black.mb-1.card-title").text()
                        val single = content.select(".name_singer.text-gray.mb-1.author").text()
                        val quality = content.select(".loss.text-pink.mb-0").text()
                        val song = Song(albumName, single, quality, thumb, url)
                        Log.e("data", content.toString())
                        lstSong.add(song)
                    }

                    listener.onLoadSongSuccess(lstSong)
                }, Response.ErrorListener { error ->
                    Log.e("VolleyError", error.toString())
                    if (!AppUtils.isOnline(context)) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.check_connection),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.error),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }) {

            }
            stringRequest.setShouldRetryServerErrors(true)
            VolleySingleton.getInstance(context).requestQueue.add(stringRequest)
        }).start()
    }

    fun getMoreNewSong(pageNumber: Int) {
        Thread(Runnable {

            var lstNewAlbum: ArrayList<Song> = ArrayList()

            var urlAlbum =
                "${AppConstants.BASE_URL}${AppConstants.NEW_MUSIC}${"?page="}${pageNumber}"
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val stringRequest = object : StringRequest(Request.Method.GET, urlAlbum,
                Response.Listener { response ->
                    val doc = Jsoup.parse(response)
                    val elmMain = doc.select(".container")
                    val elmx = elmMain.select("#music_news")
                    for (content in elmx.select(".element.mb-2")) {
                        val url = content.attr("href")
                        val elmThumb = content.select(".image100.mr-2.d-inline-block.align-middle")
                        val thumb = PareUtils.subStringNewSong(elmThumb.attr("style").toString())
                        val albumName =
                            content.select(".name_song.text-black.mb-1.card-title").text()
                        val single = content.select(".name_singer.text-gray.mb-1.author").text()
                        val quality = content.select(".loss.text-pink.mb-0").text()
                        val song = Song(albumName, single, quality, thumb, url)
                        lstNewAlbum.add(song)
                    }
                    listener.onLoadMoreSongSuccess(lstNewAlbum)
                }, Response.ErrorListener { error ->
                    Log.e("VolleyError", error.toString())
                    if (!AppUtils.isOnline(context)) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.check_connection),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.error),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }) {
            }
            stringRequest.setShouldRetryServerErrors(true)
            VolleySingleton.getInstance(context).requestQueue.add(stringRequest)
        }).start()
    }

}