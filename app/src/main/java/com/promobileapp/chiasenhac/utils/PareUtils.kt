package com.promobileapp.chiasenhac.utils

import android.util.Log

object PareUtils {
    fun subString(mainString: String): String {
        if (mainString.contains("/imgs/avatar_default.png")) {
            return AppConstants.BASE_URL + "/imgs/avatar_default.png"
        } else {
            var endString = ""
            val endIndex = mainString.indexOf(")")
            val startIndex = mainString.indexOf("http")
            endString = mainString.substring(startIndex, endIndex)
            return endString
        }
    }

    fun subStringNewSong(mainString: String): String {
        var endString = ""
        val endIndex = mainString.indexOf("')")
        val startIndex = mainString.indexOf("http")
        endString = mainString.substring(startIndex, endIndex)
        return endString
    }
}