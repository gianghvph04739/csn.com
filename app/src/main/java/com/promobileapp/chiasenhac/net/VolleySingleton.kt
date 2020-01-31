package com.promobileapp.chiasenhac.net

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton constructor(context: Context?) {
    var mRequestQueue: RequestQueue? = null

    val requestQueue: RequestQueue
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(mContext!!.applicationContext)
            }
            return mRequestQueue!!
        }

    init {
        mContext = context
        mRequestQueue =
            requestQueue
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        requestQueue.add(request)
    }

    companion object {
        private var mInstance: VolleySingleton? = null
        private var mContext: Context? = null

        @Synchronized
        fun getInstance(context: Context?): VolleySingleton {
            if (mInstance == null) {
                mInstance = VolleySingleton(context)
            }
            return mInstance!!
        }
    }
}
