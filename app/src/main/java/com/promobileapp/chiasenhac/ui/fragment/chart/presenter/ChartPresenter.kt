package com.promobileapp.chiasenhac.ui.fragment.chart.presenter

import android.content.Context
import android.os.Environment
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.model.Chart
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
import kotlin.collections.Map
import kotlin.collections.set

class ChartPresenter(private val context: Context?, private val listener: ChartListener) {

    fun loadChartVietNam() {
        Thread(Runnable {
            val listData: ArrayList<Chart> = ArrayList()
            var urlSong = "${AppConstants.BASE_URL}${AppConstants.CHART}"
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val stringRequest = object : StringRequest(Request.Method.GET, urlSong,
                Response.Listener { response ->
                    val doc = Jsoup.parse(response)
                    val elmMain = doc.select(".main")

                    /*Viet Nam*/
                    var lstVietNam: ArrayList<Song> = ArrayList()
                    val elm =
                        elmMain.select(".swiper-slide.block_bxhbaihat.block_more.tab-music.content-vietnam")
                    for (content in elm.select(".element.py-3.border-bottom")) {
                        val url = content.select("a").first().attr("href")
                        val elmThumb = content.select(".image100.mr-2.d-inline-block.align-middle")
                        val thumb = PareUtils.subStringNewSong(elmThumb.attr("style"))
                        val title =
                            content.select(".name_song.text-black.mb-1.card-title").text()
                        val artist = content.select(".name_singer.text-gray.mb-1.author").text()
                        val quality = content.select(".loss.text-pink.mb-0").text()
                        val song = Song(title, artist, quality, thumb, url)
                        lstVietNam.add(song)
                    }
                    listData.add(Chart(lstVietNam))

                    /*Us-Uk*/
                    var lstUSUK: ArrayList<Song> = ArrayList()
                    val elmUSUK =
                        elmMain.select(".swiper-slide.block_bxhbaihat.block_more.tab-music.content-us-uk")
                    for (content in elmUSUK.select(".element.py-3.border-bottom")) {
                        val url = content.select("a").first().attr("href")
                        val elmThumb = content.select(".image100.mr-2.d-inline-block.align-middle")
                        val thumb = PareUtils.subStringNewSong(elmThumb.attr("style"))
                        val title =
                            content.select(".name_song.text-black.mb-1.card-title").text()
                        val artist = content.select(".name_singer.text-gray.mb-1.author").text()
                        val quality = content.select(".loss.text-pink.mb-0").text()
                        val song = Song(title, artist, quality, thumb, url)
                        lstUSUK.add(song)
                    }
                    listData.add(Chart(lstUSUK))

                    /*ChiNa*/
                    var lstChina: ArrayList<Song> = ArrayList()
                    val elmChina =
                        elmMain.select(".swiper-slide.block_bxhbaihat.block_more.tab-music.content-chinese")
                    for (content in elmChina.select(".element.py-3.border-bottom")) {
                        val url = content.select("a").first().attr("href")
                        val elmThumb = content.select(".image100.mr-2.d-inline-block.align-middle")
                        val thumb = PareUtils.subStringNewSong(elmThumb.attr("style"))
                        val title =
                            content.select(".name_song.text-black.mb-1.card-title").text()
                        val artist = content.select(".name_singer.text-gray.mb-1.author").text()
                        val quality = content.select(".loss.text-pink.mb-0").text()
                        val song = Song(title, artist, quality, thumb, url)
                        lstChina.add(song)
                    }
                    listData.add(Chart(lstChina))

                    /*Korea*/
                    var lstKorea: ArrayList<Song> = ArrayList()
                    val elmKorea =
                        elmMain.select(".swiper-slide.block_bxhbaihat.block_more.tab-music.content-korea")
                    for (content in elmKorea.select(".element.py-3.border-bottom")) {
                        val url = content.select("a").first().attr("href")
                        val elmThumb = content.select(".image100.mr-2.d-inline-block.align-middle")
                        val thumb = PareUtils.subStringNewSong(elmThumb.attr("style"))
                        val title =
                            content.select(".name_song.text-black.mb-1.card-title").text()
                        val artist = content.select(".name_singer.text-gray.mb-1.author").text()
                        val quality = content.select(".loss.text-pink.mb-0").text()
                        val song = Song(title, artist, quality, thumb, url)
                        lstKorea.add(song)
                    }
                    listData.add(Chart(lstKorea))

                    /*Japan*/
                    var lstJapan: ArrayList<Song> = ArrayList()
                    val elmJapan =
                        elmMain.select(".swiper-slide.block_bxhbaihat.block_more.tab-music.content-japan")
                    for (content in elmJapan.select(".element.py-3.border-bottom")) {
                        val url = content.select("a").first().attr("href")
                        val elmThumb = content.select(".image100.mr-2.d-inline-block.align-middle")
                        val thumb = PareUtils.subStringNewSong(elmThumb.attr("style"))
                        val title =
                            content.select(".name_song.text-black.mb-1.card-title").text()
                        val artist = content.select(".name_singer.text-gray.mb-1.author").text()
                        val quality = content.select(".loss.text-pink.mb-0").text()
                        val song = Song(title, artist, quality, thumb, url)
                        lstJapan.add(song)
                    }
                    listData.add(Chart(lstJapan))


                    /*france*/
                    var lstFrance: ArrayList<Song> = ArrayList()
                    val elmFrance =
                        elmMain.select(".swiper-slide.block_bxhbaihat.block_more.tab-music.content-france")
                    for (content in elmFrance.select(".element.py-3.border-bottom")) {
                        val url = content.select("a").first().attr("href")
                        val elmThumb = content.select(".image100.mr-2.d-inline-block.align-middle")
                        val thumb = PareUtils.subStringNewSong(elmThumb.attr("style"))
                        val title =
                            content.select(".name_song.text-black.mb-1.card-title").text()
                        val artist = content.select(".name_singer.text-gray.mb-1.author").text()
                        val quality = content.select(".loss.text-pink.mb-0").text()
                        val song = Song(title, artist, quality, thumb, url)
                        lstFrance.add(song)
                    }
                    listData.add(Chart(lstFrance))

                    /*other*/
                    var lstOther: ArrayList<Song> = ArrayList()
                    val elmOther =
                        elmMain.select(".swiper-slide.block_bxhbaihat.block_more.tab-music.content-other")
                    for (content in elmOther.select(".element.py-3.border-bottom")) {
                        val url = content.select("a").first().attr("href")
                        val elmThumb = content.select(".image100.mr-2.d-inline-block.align-middle")
                        val thumb = PareUtils.subStringNewSong(elmThumb.attr("style"))
                        val title =
                            content.select(".name_song.text-black.mb-1.card-title").text()
                        val artist = content.select(".name_singer.text-gray.mb-1.author").text()
                        val quality = content.select(".loss.text-pink.mb-0").text()
                        val song = Song(title, artist, quality, thumb, url)
                        lstOther.add(song)
                    }
                    listData.add(Chart(lstOther))

                    /*other*/
                    var lstPlayback: ArrayList<Song> = ArrayList()
                    val elmPlayback =
                        elmMain.select(".swiper-slide.block_bxhbaihat.block_more.tab-music.content-beat-playback")
                    for (content in elmPlayback.select(".element.py-3.border-bottom")) {
                        val url = content.select("a").first().attr("href")
                        val elmThumb = content.select(".image100.mr-2.d-inline-block.align-middle")
                        val thumb = PareUtils.subStringNewSong(elmThumb.attr("style"))
                        val title =
                            content.select(".name_song.text-black.mb-1.card-title").text()
                        val artist = content.select(".name_singer.text-gray.mb-1.author").text()
                        val quality = content.select(".loss.text-pink.mb-0").text()
                        val song = Song(title, artist, quality, thumb, url)
                        lstPlayback.add(song)
                    }
                    listData.add(Chart(lstPlayback))

                    listener.onLoadChartDone(listData)
                }, Response.ErrorListener { error ->
                    Log.e("VolleyError", error.toString())
                    if (!AppUtils.isOnline(context)) {
                        Toast.makeText(
                            context,
                            context?.getString(R.string.check_connection),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            context?.getString(R.string.error),
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
                FileWriter(File(Environment.getExternalStorageDirectory().path, "hotChart.html"))
            out.write(fileContents)
            out.close()
            Toast.makeText(context, "done", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Log.e("Eror", e.message)
        }

    }
}