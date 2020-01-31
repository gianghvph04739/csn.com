package com.promobileapp.chiasenhac.ui.activity.search.presenter

import android.content.Context
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.model.Song
import com.promobileapp.chiasenhac.net.VolleySingleton
import com.promobileapp.chiasenhac.utils.AppConstants
import com.promobileapp.chiasenhac.utils.AppUtils
import com.promobileapp.chiasenhac.utils.PareUtils
import org.jsoup.Jsoup
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class SearchPresenter(private val context: Context, private val listener: SearchListener) {
    fun search(name: String) {
        Thread(Runnable {
            var dataSearch: ArrayList<Song> = ArrayList()
            var urlAlbum = "${AppConstants.SEARCH_BASE_URL}${name?.replace(" ", "%20")}"
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val stringRequest = object : StringRequest(Request.Method.GET, urlAlbum, Response.Listener { response ->
                val doc = Jsoup.parse(response)
                val elmMain = doc.select(".main")
                val elm = elmMain.select(".block.block_baihat").first()
                val elmContent = elm.select(".block_bxhbaihat.block_more")
                for (content in elmContent.select(".element.mb-2")) {
                    val urlElm = content.select("a").first()
                    val url = urlElm.attr("href")
                    val elmThumb = content.select(".image100.mr-2.d-inline-block.align-middle")
                    val thumb = PareUtils.subStringNewSong(elmThumb.attr("style").toString())
                    val albumName = content.select(".name_song.text-black.mb-1.card-title").text()
                    val single = content.select(".name_singer.text-gray.mb-1.author").text()
                    val quality = content.select(".loss.text-pink.mb-0").text()
                    val song = Song(albumName, single, quality, thumb, url)
                    Log.e("Song", song.toString())
                    dataSearch.add(song)
                }
                listener.onSearchDone(dataSearch)
            }, Response.ErrorListener { error ->
                Log.e("VolleyError", error.toString())
                if (!AppUtils.isOnline(context)) {
                    Toast.makeText(
                            context, context.getString(R.string.check_connection), Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                            context, context.getString(R.string.error), Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] = "Mozilla/5.0 (Windows Phone 10.0; Android 6.0.1; Microsoft; Lumia 650) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Mobile Safari/537.36 Edge/15.15063"
                    return headers
                }
            }
            stringRequest.retryPolicy = DefaultRetryPolicy(
                    15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            stringRequest.setShouldRetryServerErrors(true)
            VolleySingleton.getInstance(context).requestQueue.add(stringRequest)
        }).start()
    }

    fun search(name: String, page: Int) {
        Thread(Runnable {
            var dataSearch: ArrayList<Song> = ArrayList()
            var urlAlbum = "${AppConstants.SEARCH_BASE_URL}" + "${name?.replace(" ", "%20")}" + "&page_music=" + page + "&tab=music"
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val stringRequest = object : StringRequest(Request.Method.GET, urlAlbum, Response.Listener { response ->
                val doc = Jsoup.parse(response)
                val elmMain = doc.select(".main")
                val elm = elmMain.select(".block.block_baihat").first()
                val elmContent = elm.select(".block_bxhbaihat.block_more")
                for (content in elmContent.select(".element.mb-2")) {
                    val urlElm = content.select("a").first()
                    val url = urlElm.attr("href")
                    val elmThumb = content.select(".image100.mr-2.d-inline-block.align-middle")
                    val thumb = PareUtils.subStringNewSong(elmThumb.attr("style").toString())
                    val albumName = content.select(".name_song.text-black.mb-1.card-title").text()
                    val single = content.select(".name_singer.text-gray.mb-1.author").text()
                    val quality = content.select(".loss.text-pink.mb-0").text()
                    val song = Song(albumName, single, quality, thumb, url)
                    Log.e("Song", song.toString())
                    dataSearch.add(song)
                }
                listener.onSearchMore(dataSearch)
            }, Response.ErrorListener { error ->
                Log.e("VolleyError", error.toString())
                if (!AppUtils.isOnline(context)) {
                    Toast.makeText(
                            context, context.getString(R.string.check_connection), Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                            context, context.getString(R.string.error), Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] = "Mozilla/5.0 (Windows Phone 10.0; Android 6.0.1; Microsoft; Lumia 650) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Mobile Safari/537.36 Edge/15.15063"
                    return headers
                }
            }
            stringRequest.setShouldRetryServerErrors(true)
            VolleySingleton.getInstance(context).requestQueue.add(stringRequest)
        }).start()
    }
}