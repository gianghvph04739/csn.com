package com.promobileapp.chiasenhac.ui.activity.list.presenter

import android.content.Context
import android.os.Environment
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
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
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class ListPresenter(private val context: Context, private val listener: ListListener) {
    fun getListSong(url: String) {
        Thread(Runnable {
            var lstSong: ArrayList<Song> = ArrayList()
            var urlAlbum = "${AppConstants.BASE_URL}${url}"
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val stringRequest = object : StringRequest(Request.Method.GET, urlAlbum,
                Response.Listener { response ->
                    val doc = Jsoup.parse(response)
                    val elm = doc.select(".block_baihat_main.block_more.music_recommendation")
                    for (content in elm.select(".element.mb-2.card-footer")) {
                        val link = content.select("a").first()
                        val url = link.attr("href")
                        val elmThumb = content.select(".image.image100px.mr-2.d-inline-block.align-middle")
                        val thumb = PareUtils.subString(elmThumb.attr("style").toString())
                        val albumName = content.select(".name_song.text-black.mb-1.card-title").text()
                        val single = content.select(".name_singer.text-gray.mb-1.author").text()
                        val quality = content.select(".loss.text-pink.mb-0").text()
                        val song = Song(albumName, single, quality, thumb, url)
                        Log.e("Song", song.toString())
                        lstSong.add(song)
                    }
                    listener.onLoadListSongSuccesfull(lstSong)
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

    fun writeStringAsFile(fileContents: String) {
        try {
            val out =
                FileWriter(File(Environment.getExternalStorageDirectory().path, "list.html"))
            out.write(fileContents)
            out.close()
            Toast.makeText(context, "done", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Log.e("Eror", e.message)
        }
    }
}