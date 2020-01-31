package com.promobileapp.chiasenhac.ui.fragment.album.presenter

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

class AlbumPresenter(mContext: Context, mListener: AlbumListener) {
    var context = mContext
    var listener = mListener

    fun getNewAlbum() {
        Thread(Runnable {

            var lstNewAlbum: ArrayList<Album> = ArrayList()

            var urlAlbum = "${AppConstants.BASE_URL}${AppConstants.NEW_ALBUM}"
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val stringRequest = object : StringRequest(Request.Method.GET, urlAlbum,
                Response.Listener { response ->
                    val doc = Jsoup.parse(response)
                    val elmMain = doc.select(".container")
                    val elmx = elmMain.select("#cover_news")
                    for (content in elmx.select(".col")) {
                        val elmLink = content.select("a").first()
                        val url = elmLink.attr("href")
                        val elmThumb = content.select(".card-header")
                        val thumb = PareUtils.subString(elmThumb.attr("style").toString())
                        val albumName = content.select(".card-title").text()
                        val single = content.select(".card-text").text()
                        val quality = ""
                        val song = Album(albumName, single, thumb, quality, url)
                        lstNewAlbum.add(song)
                    }
                    listener.onLoadAlbumSuccess(lstNewAlbum)
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
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] =
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_8) AppleWebKit/534.50.2 (KHTML, like Gecko) Version/5.0.6 Safari/533.22.3"
                    return headers
                }
            }
            stringRequest.setShouldRetryServerErrors(true)
            VolleySingleton.getInstance(context).requestQueue.add(stringRequest)
        }).start()
    }

    fun getMoreNewAlbum(pageNumber: Int) {
        Thread(Runnable {

            var lstNewAlbum: ArrayList<Album> = ArrayList()

            var urlAlbum =
                "${AppConstants.BASE_URL}${AppConstants.NEW_ALBUM}${"?page="}${pageNumber}"
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val stringRequest = object : StringRequest(Request.Method.GET, urlAlbum,
                Response.Listener { response ->
                    val doc = Jsoup.parse(response)
                    val elmMain = doc.select(".container")
                    val elmx = elmMain.select("#cover_news")

                    for (content in elmx.select(".col")) {
                        val elmLink = content.select("a").first()
                        val url = elmLink.attr("href")

                        val elmThumb = content.select(".card-header")
                        val thumb = PareUtils.subString(elmThumb.attr("style").toString())

                        val albumName = content.select(".card-title").text()
                        val single = content.select(".card-text").text()
                        val quality = ""
                        val song = Album(albumName, single, thumb, quality, url)
                        lstNewAlbum.add(song)
                    }
                    listener.onLoadMoreAlbumSuccess(lstNewAlbum)
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
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] =
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_5_8) AppleWebKit/534.50.2 (KHTML, like Gecko) Version/5.0.6 Safari/533.22.3"
                    return headers
                }
            }
            stringRequest.setShouldRetryServerErrors(true)
            VolleySingleton.getInstance(context).requestQueue.add(stringRequest)
        }).start()
    }

}