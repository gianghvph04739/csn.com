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
import com.promobileapp.chiasenhac.model.Song
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
    fun getListQualitySong(song: Song?) {
        Thread(Runnable {
            var urlSong = song?.urlSong
            var lstQuality: ArrayList<Quality> = ArrayList()
            if (urlSong != null) {
                if (!urlSong.contains(AppConstants.BASE_URL)) {
                    urlSong = "${AppConstants.BASE_URL}${urlSong}"
                }
            }
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val stringRequest = object : StringRequest(Method.GET, urlSong, Response.Listener { response ->
                val doc = Jsoup.parse(response)
                val elm = doc.select(".bottom-sheet.select-quality.text-center.popup-download")
                val elmQuality = elm.select(".form-group.download_status")
                for (content in elmQuality.select(".relative")) {
                    val elmUrl = content.select("input").first()
                    val url = elmUrl.attr("value")
                    val qualityMusic = content.select("span").get(1).text()
                    val size = content.select("span").get(2).text()
                    val quality = Quality(song?.title, url, qualityMusic, size)
                    Log.e("Song", quality.toString())
                    lstQuality.add(quality)
                }
                if(lstQuality.size>0){
                    listener.onLoadListQualitySuccesfull(lstQuality)
                } else{
                    listener.onLoadListqualityError()
                }
            }, Response.ErrorListener { error ->
                Log.e("VolleyError", error.message)
                if (!AppUtils.isOnline(context)) {
                    Toast.makeText(
                            context, context.getString(R.string.check_connection), Toast.LENGTH_SHORT
                    ).show()
                    listener.onLoadListqualityError()
                } else {
                    Toast.makeText(
                            context, context.getString(R.string.error), Toast.LENGTH_SHORT
                    ).show()
                    listener.onLoadListqualityError()
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