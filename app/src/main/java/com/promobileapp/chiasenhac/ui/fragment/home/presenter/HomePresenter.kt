package com.promobileapp.chiasenhac.ui.fragment.home.presenter

import android.content.Context
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.model.Album
import com.promobileapp.chiasenhac.net.VolleySingleton
import com.promobileapp.chiasenhac.utils.AppConstants
import com.promobileapp.chiasenhac.utils.AppUtils
import com.promobileapp.chiasenhac.utils.PareUtils
import org.jsoup.Jsoup
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.Map
import kotlin.collections.set

class HomePresenter(mContext: Context, mListener: HomeListener) {
    var context = mContext
    var listener = mListener


    fun loadBanner() {
        Thread(Runnable {
            var lstBanner: ArrayList<Album> = ArrayList()

            var urlSong = AppConstants.BASE_URL
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val stringRequest = object : StringRequest(Request.Method.GET, urlSong,
                Response.Listener { response ->
                    val doc = Jsoup.parse(response)
                    val elmMain = doc.select(".main")
                    val elm = elmMain.select(".swiper-slide").first()
                    val elmContent = elm.select("#pills-home")
                    val elmx = elmContent.select(".owl-carousel.owl-theme.slider_home.pt-3")
                    for (content in elmx.select("a")) {
                        val url = content.attr("href")
                        val elmThumb = content.select(".item")
                        val thumb = PareUtils.subString(elmThumb.attr("style").toString())
                        val albumName = content.select(".name_song.mb-1").text()
                        val single = content.select(".name_singer.mb-1.author").text()
                        val quality = content.select(".loss.text-pink.mb-0").text()
                        val banner = Album(albumName, single, thumb, quality, url)
                        lstBanner.add(banner)
                    }
                    listener.onLoadBannerSuccessFull(lstBanner)
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
                        ).show()
                    }
                }) {
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] =
                        "Mozilla/5.0 (Windows Phone 10.0; Android 6.0.1; Microsoft; Lumia 650) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Mobile Safari/537.36 Edge/15.15063"
                    return headers
                }
            }
            stringRequest.setShouldRetryServerErrors(true)
            VolleySingleton.getInstance(context).requestQueue.add(stringRequest)
        }).start()
    }

    fun loadNewAlbum() {
        Thread(Runnable {
            var lstAlbum: ArrayList<Album> = ArrayList()
            var urlSong = AppConstants.BASE_URL
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val stringRequest = object : StringRequest(Request.Method.GET, urlSong,
                Response.Listener { response ->
                    val doc = Jsoup.parse(response)
                    val elmMain = doc.select(".main")
                    val elm = elmMain.select(".swiper-slide").first()
                    val elmContent = elm.select("#pills-home")
                    val elmContainer = elmContent.select(".container")
                    val elmBxh = elmContainer.select(".block.block_album").first()
                    for (content in elmBxh.select(".item.element")) {
                        val url = content.select("a").first().attr("href")
                        val elmThumb = content.select(".image.rounded")
                        val thumb = PareUtils.subString(elmThumb.attr("style"))
                        val albumName = content.select(".name_song.mb-1.card-title").text()
                        val quality = content.select(".loss.text-pink.mb-0").text()
                        val single = content.select(".name_singer.text-gray.mb-1.author").text()
                        val album = Album(albumName, single, thumb, quality, url)
                        lstAlbum.add(album)
                    }
                    listener.onLoadNewAlbumSuccessFull(lstAlbum)
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
                        ).show()
                    }
                }) {
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] =
                        "Mozilla/5.0 (Windows Phone 10.0; Android 6.0.1; Microsoft; Lumia 650) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Mobile Safari/537.36 Edge/15.15063"
                    return headers
                }
            }
            stringRequest.setShouldRetryServerErrors(true)
            VolleySingleton.getInstance(context).requestQueue.add(stringRequest)
        }).start()
    }

}