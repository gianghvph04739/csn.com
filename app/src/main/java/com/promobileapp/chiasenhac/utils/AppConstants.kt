package com.promobileapp.chiasenhac.utils

import android.content.Context
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.model.Category
import java.util.*

object AppConstants {
    val ALBUM_INTENT = "album_intent"
    val CATEGORY_INTENT = "category_intent"
    val SEARCH_BASE_URL = "https://chiasenhac.vn/tim-kiem?q="
    val BASE_URL = "https://chiasenhac.vn"
    val CHART_VN = "/nhac-hot/vietnam.html"
    val CHART_US_UK = "/nhac-hot/us-uk.html"
    val CHART_JP = "/nhac-hot/japan.html"
    val CHART_KR = "/nhac-hot/korea.html"
    val CHART = "/nhac-hot.html"
    val NEW_MUSIC = "/bai-hat-moi.html"
    val NEW_ALBUM = "/album-moi.html"
    val TRENDING = "TRENDING"
    val COUT_NAME = "COUT_NAME"
    val POSITION_TRENDING = "POSITION_TRENDING"
    val CATE_ROMANCE = "/chu-de/romance.html"
    val CATE_SLEEP = "/chu-de/sleep.html"
    val CATE_GYM = "/chu-de/gym.html"
    val CATE_DANCE = "/chu-de/dance.html"
    val CATE_WORK = "/chu-de/work.html"
    val CATE_COFFE = "/chu-de/coffee.html"
    val CATE_GAME = "/chu-de/game.html"
    val CATE_TRAVEL = "/chu-de/travel.html"
    val CATE_NEW_YEAR = "/chu-de/newyear.html"
    fun getListNation(context: Context): ArrayList<Category> {
        val lstNation = ArrayList<Category>()
        lstNation.add(Category(R.drawable.top_chart_vn, context.getString(R.string.viet_nam), CHART_VN))
        lstNation.add(Category(R.drawable.top_chart_us, context.getString(R.string.us), CHART_US_UK))
        lstNation.add(Category(R.drawable.top_chart_kr, context.getString(R.string.korean), CHART_KR))
        lstNation.add(Category(R.drawable.top_chart_jp, context.getString(R.string.japan), CHART_JP))
        return lstNation
    }

    fun getListCategory(context: Context?): ArrayList<Category> {
        val lstNation = ArrayList<Category>()
        if (context != null) {
            lstNation.add(Category(R.drawable.bg_romane, context.getString(R.string.cate_romance), CATE_ROMANCE))
            lstNation.add(Category(R.drawable.bg_sleep, context.getString(R.string.cate_sleep), CATE_SLEEP))
            lstNation.add(Category(R.drawable.bg_gym, context.getString(R.string.cate_gym), CATE_GYM))
            lstNation.add(Category(R.drawable.bg_dance, context.getString(R.string.cate_dance), CATE_DANCE))
            lstNation.add(Category(R.drawable.bg_work, context.getString(R.string.cate_work), CATE_WORK))
            lstNation.add(Category(R.drawable.bg_coffe, context.getString(R.string.cate_coffe), CATE_COFFE))
            lstNation.add(Category(R.drawable.bg_game, context.getString(R.string.cate_game), CATE_GAME))
            lstNation.add(Category(R.drawable.bg_travel, context.getString(R.string.cate_travel), CATE_TRAVEL))
            lstNation.add(Category(R.drawable.bg_newyear, context.getString(R.string.cate_newyear), CATE_NEW_YEAR))
        }
        return lstNation
    }

    fun getListChart(context: Context?): List<String> {
        if (context == null)
            return arrayListOf()
        val list: List<String> =
            listOf(context.getString(R.string.viet_nam), context.getString(R.string.us_uk), context.getString(R.string.china),
                context.getString(R.string.korean), context.getString(R.string.japan), context.getString(R.string.france),
                context.getString(R.string.other), context.getString(R.string.playback))
        return list
    }
}