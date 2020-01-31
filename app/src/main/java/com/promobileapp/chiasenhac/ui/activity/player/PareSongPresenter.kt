package com.promobileapp.chiasenhac.ui.activity.player

import android.content.Context
import android.os.Environment
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.model.Quality
import com.promobileapp.chiasenhac.net.VolleySingleton
import com.promobileapp.chiasenhac.utils.AppConstants
import com.promobileapp.chiasenhac.utils.AppUtils
import org.jsoup.Jsoup
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class PareSongPresenter(private val context: Context, private val listener: PareSongListener) {
    fun getListQualitySong(url: String?) {
        Thread(Runnable {
            var urlSong = url
            var lstQuality: ArrayList<Quality> = ArrayList()
            if (url != null) {
                if (!url.contains(AppConstants.BASE_URL)) {
                    urlSong = "${AppConstants.BASE_URL}${url}"
                }
            }
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val stringRequest = object : StringRequest(Method.GET, urlSong, Response.Listener { response ->
                val doc = Jsoup.parse(response)
                val elm = doc.select(".bottom-sheet.select-quality.text-center.popup-download")
                val elmQuality = elm.select(".form-group.download_status")
                for (content in elmQuality.select(".relative")) {
                    //                        val link = content.select("a").first()
                    //                        val url = link.attr("href")
                    //                        val elmThumb = content.select(".image.image100px.mr-2.d-inline-block.align-middle")
                    //                        val thumb = PareUtils.subString(elmThumb.attr("style").toString())
                    //                        val albumName = content.select(".name_song.text-black.mb-1.card-title").text()
                    //                        val single = content.select(".name_singer.text-gray.mb-1.author").text()
                    //                        val quality = content.select(".loss.text-pink.mb-0").text()
                    //                        val song = Song(albumName, single, quality, thumb, url)
                    Log.e("Song", content.toString())
                    //                        lstQuality.add(song)
                }
            }, Response.ErrorListener { error ->
                Log.e("VolleyError", error.message)
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

    fun writeStringAsFile(fileContents: String) {
        try {
            val out = FileWriter(File(Environment.getExternalStorageDirectory().path, "list.html"))
            out.write(fileContents)
            out.close()
            Toast.makeText(context, "done", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Log.e("Eror", e.message)
        }
    }
}