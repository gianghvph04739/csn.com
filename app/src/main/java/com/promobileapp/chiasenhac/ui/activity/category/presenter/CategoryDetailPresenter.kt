package com.promobileapp.chiasenhac.ui.activity.category.presenter

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
import kotlin.collections.set

class CategoryDetailPresenter(private val context: Context, private val listener: CategoryDetailListener) {
    fun getListDetail(url: String?) {
        Thread(Runnable {
            var lstAlbum: ArrayList<Album> = ArrayList()
            var urlAlbum = "${AppConstants.BASE_URL}${url}"
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val stringRequest = object : StringRequest(Request.Method.GET, urlAlbum,
                Response.Listener { response ->
                    val doc = Jsoup.parse(response)
                    val elm = doc.select(".block.block_album.block_profile_playlist")
                    val elmRow = elm.select(".row.row-sm")
                    for (content in elmRow.select(".col-6")) {
                        val link = content.select("a").first()
                        val url = link.attr("href")
                        val elmThumb = content.select(".image.rounded")
                        val thumb = PareUtils.subString(elmThumb.attr("style").toString())
                        val albumName = content.select(".name_song.mb-1.card-title").text()
                        val single = content.select(".name_singer.text-gray.mb-1.author").text()
                        val quality = ""
                        val album = Album(albumName, single, thumb, quality, url)
                        Log.e("Album", album.toString())
                        lstAlbum.add(album)
                    }
                    listener.onLoadListDetailDone(lstAlbum)
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
                    listener.onLoadListDetailFaied()
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