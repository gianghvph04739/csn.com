package com.promobileapp.chiasenhac.utils

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.DisplayMetrics

object AppUtils {
    fun isOnline(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var netInfo: NetworkInfo? = null
        netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    fun pxToDp(px: Float): Int {
        val displayMetrics = Resources.getSystem().displayMetrics
        return Math.round(px / (displayMetrics.density / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun dpToPx(dp: Float): Int {
        val displayMetrics = Resources.getSystem().displayMetrics
        return Math.round(dp * (displayMetrics.density / DisplayMetrics.DENSITY_DEFAULT))
    }
}