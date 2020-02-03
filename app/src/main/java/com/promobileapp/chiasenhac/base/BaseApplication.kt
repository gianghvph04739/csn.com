package com.promobileapp.chiasenhac.base

import android.app.Application
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.*
import com.google.android.exoplayer2.util.Util
import com.promobileapp.chiasenhac.BuildConfig
import java.io.File

class BaseApplication : Application() {
    private val DOWNLOAD_ACTION_FILE = "actions"
    private val DOWNLOAD_TRACKER_ACTION_FILE = "tracked_actions"
    private val DOWNLOAD_CONTENT_DIRECTORY = "downloads"
    private val MAX_SIMULTANEOUS_DOWNLOADS = 2
    private var downloadDirectory: File? = null
    private var downloadCache: Cache? = null
    var userAgent = ""
    override fun onCreate() {
        super.onCreate()
        userAgent = Util.getUserAgent(this, "ExoPlayer")
    }

    /**
     * Returns a [DataSource.Factory].
     */
    fun buildDataSourceFactory(): DataSource.Factory? {
        val upstreamFactory = DefaultDataSourceFactory(this, buildHttpDataSourceFactory())
        return buildReadOnlyCacheDataSource(upstreamFactory, getDownloadCache())
    }

    /**
     * Returns a [HttpDataSource.Factory].
     */
    fun buildHttpDataSourceFactory(): HttpDataSource.Factory? {
        return DefaultHttpDataSourceFactory(userAgent)
    }

    /**
     * Returns whether extension renderers should be used.
     */
    fun useExtensionRenderers(): Boolean {
        return "withExtensions" == BuildConfig.FLAVOR
    }

    @Synchronized private fun getDownloadCache(): Cache {
        if (downloadCache == null) {
            val downloadContentDirectory = File(getDownloadDirectory(), DOWNLOAD_CONTENT_DIRECTORY)
            downloadCache = SimpleCache(downloadContentDirectory, NoOpCacheEvictor())
        }
        return downloadCache as Cache
    }

    private fun getDownloadDirectory(): File? {
        if (downloadDirectory == null) {
            downloadDirectory = getExternalFilesDir(null)
            if (downloadDirectory == null) {
                downloadDirectory = filesDir
            }
        }
        return downloadDirectory
    }

    private fun buildReadOnlyCacheDataSource(upstreamFactory: DefaultDataSourceFactory, cache: Cache): CacheDataSourceFactory? {
        return CacheDataSourceFactory(
                cache, upstreamFactory, FileDataSourceFactory(),  /* cacheWriteDataSinkFactory= */
                null, CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,  /* eventListener= */
                null
        )
    }
}