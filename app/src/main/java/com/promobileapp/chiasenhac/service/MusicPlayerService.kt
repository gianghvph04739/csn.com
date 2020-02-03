package com.promobileapp.chiasenhac.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.ParametersBuilder
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.util.EventLogger
import com.google.android.exoplayer2.util.Util
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.base.BaseApplication
import com.promobileapp.chiasenhac.ui.activity.player.PlayerActivity
import com.promobileapp.chiasenhac.utils.AppConstants

class MusicPlayerService : Service() {
    val name: CharSequence = "CSNGIANGHV"
    val NOTIFICATION_ID = 125999
    private val CHANNEL_NOTIFICATION_ID = "com.promobileapp.csn"
    private val mBinder: IBinder = MusicServiceBinder()
    var isServiceRunning: Boolean = false
    private lateinit var mBuilder: NotificationCompat.Builder
    lateinit var notificationManager: NotificationManager
    private var dataSourceFactory: DataSource.Factory? = null
    private var mExoPlayer: SimpleExoPlayer? = null
    private var mediaSource: MediaSource? = null
    private var trackSelector: DefaultTrackSelector? = null
    private var trackSelectorParameters: DefaultTrackSelector.Parameters? = null
    private var audioManager: AudioManager? = null
    private val mOnAudioFocusChangeListener_ = OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> {
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
            }
            AudioManager.AUDIOFOCUS_LOSS -> if (isServiceRunning) if (mExoPlayer != null) {
                try {
                    if (mExoPlayer!!.playWhenReady) {
                        //                        playSong()
                    }

                } catch (ex: Exception) {
                }
            }
        }
    }

    inner class MusicServiceBinder : Binder() {
        fun getService(): MusicPlayerService? {
            return this@MusicPlayerService
        }
    }

    override fun onCreate() {
        super.onCreate()
        audioManager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    fun showMessage(mess: String) {
        Toast.makeText(this, mess, Toast.LENGTH_LONG).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val keepSerrvice = START_STICKY //1

        if (intent != null && intent.action != null) {
            when (intent.action) {
                AppConstants.ACTION_SET_DATASOURCE -> {
                    var url = intent.getStringExtra(AppConstants.ACTION_SET_DATASOURCE)
                    showMessage(url)
                    setDatasource(url)
                }
            }
        }
        return keepSerrvice
    }

    override fun onDestroy() {
        isServiceRunning = false
        super.onDestroy()
    }

    private fun showNotification() {
        if (isServiceRunning) return
        isServiceRunning = true
        createNotificationChannel()
        createNotification(true)
        startForeground(NOTIFICATION_ID, mBuilder.build())
    }

    private fun createNotificationChannel() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    CHANNEL_NOTIFICATION_ID, name, NotificationManager.IMPORTANCE_LOW
            )
            channel.enableVibration(false)
            channel.enableLights(true)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(play: Boolean) {
        val music_next = Intent(AppConstants.ACTION_NEXT)
        val music_prive = Intent(AppConstants.ACTION_PRIVE)
        val music_stop = Intent(AppConstants.ACTION_STOP)
        val music_playpause = Intent(AppConstants.ACTION_TOGGLE_PLAY)
        val pendingPlayPause = PendingIntent.getBroadcast(
                this, 0, music_playpause, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val pendingNext = PendingIntent.getBroadcast(
                this, 0, music_next, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val pendingPrive = PendingIntent.getBroadcast(
                this, 0, music_prive, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val pendingStop = PendingIntent.getBroadcast(
                this, 0, music_stop, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notificationIntent = Intent(applicationContext, PlayerActivity::class.java)
        notificationIntent.action = AppConstants.ACTION_MAIN_OPEN
        notificationIntent.putExtra(AppConstants.OPEN_FROM_NOTIFICATION, true)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val contentPendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT
        )
        val contentTittle = "Content title"
        val contentText = "Content Text"

        if (!play) {
            mBuilder = NotificationCompat.Builder(
                    applicationContext, CHANNEL_NOTIFICATION_ID
            )
            mBuilder.setSmallIcon(R.drawable.ic_launcher_background).setShowWhen(false).setContentTitle(contentTittle).setContentText(contentText).setSubText(getString(R.string.now_playing)).setContentIntent(contentPendingIntent).addAction(R.drawable.ic_prive, "Prive", pendingPrive).addAction(R.drawable.ic_pause_60, "Pause", pendingPlayPause).addAction(R.drawable.ic_next, "next", pendingNext).addAction(R.drawable.ic_close, "Pause", pendingStop).setColor(Color.parseColor("#5a9216")).setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(
                            0, 1, 2
                    )
            )
        } else {
            mBuilder = NotificationCompat.Builder(
                    applicationContext, CHANNEL_NOTIFICATION_ID
            )
            mBuilder.setSmallIcon(R.drawable.ic_launcher_background).setShowWhen(false).setContentTitle(contentTittle).setContentText(contentText).setSubText(getString(R.string.now_playing)).setContentIntent(contentPendingIntent).addAction(R.drawable.ic_prive, "Prive", pendingPrive).addAction(R.drawable.ic_pause_60, "Pause", pendingPlayPause).addAction(R.drawable.ic_next, "next", pendingNext).addAction(R.drawable.ic_close, "Pause", pendingStop).setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(
                            0, 1, 2
                    )
            )
        }
    }

    private fun setUpNotificationData() {
        val contentTittle = "Content title"
        val contentText = "Content Text"

        mBuilder.setContentTitle(contentTittle)
        mBuilder.setContentText(contentText)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //        if (mediaPlayer != null) {
        //            if (isPlaying()) {
        //                mBuilder.mActions[1].icon = R.drawable.noti_pause
        //                notificationManager.notify(NOTIFICATION_ID, mBuilder.build())
        //            } else {
        //                mBuilder.mActions[1].icon = R.drawable.noti_play
        //                notificationManager.notify(
        //                        NOTIFICATION_ID, mBuilder.build()
        //                )
        //            }
        //        }
        setArtWork()
    }

    private fun updateStatusNotification() {
        //        if (mediaPlayer != null) {
        //            if (isPlaying()) {
        //                mBuilder.mActions[1].icon = R.drawable.noti_pause
        //                notificationManager.notify(NOTIFICATION_ID, mBuilder.build())
        //            } else {
        //                mBuilder.mActions[1].icon = R.drawable.noti_play
        //                notificationManager.notify(
        //                        NOTIFICATION_ID, mBuilder.build()
        //                )
        //            }
        //        }
    }

    fun setArtWork() {
        //        imgAvt?.let {
        //            GlideApp.with(this).load(getCurrent()?.albumId?.let { ArtworkUtils.uri(it) }).placeholder(R.drawable.default_thumb_song).into(it)
        //        }
        //
        //        GlideApp.with(this).asBitmap().load(getCurrent()?.albumId?.let { ArtworkUtils.uri(it) }).into(object : CustomTarget<Bitmap?>() {
        //                override fun onLoadFailed(errorDrawable: Drawable?) {
        //                    val bitmap = BitmapFactory.decodeResource(
        //                            resources, R.drawable.default_thumb_song
        //                    )
        //                    mBuilder.setLargeIcon(bitmap)
        //                    notificationManager.notify(
        //                            NOTIFICATION_ID, mBuilder.build()
        //                    )
        //                }
        //
        //                override fun onLoadCleared(placeholder: Drawable?) {}
        //                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
        //                    mBuilder.setLargeIcon(resource)
        //                    notificationManager.notify(
        //                            NOTIFICATION_ID, mBuilder.build()
        //                    )
        //                }
        //            })
    }

    fun setDatasource(url: String?) {
        releasePlayer()
        dataSourceFactory = buildDataSourceFactory()
        trackSelectorParameters = ParametersBuilder().build()
        val trackSelectionFactory: TrackSelection.Factory
        trackSelectionFactory = AdaptiveTrackSelection.Factory()
        val renderersFactory = DefaultRenderersFactory(this)
        trackSelector = DefaultTrackSelector(trackSelectionFactory)
        trackSelector!!.parameters = trackSelectorParameters
        val defaultLoadControl = DefaultLoadControl()
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(applicationContext, renderersFactory, trackSelector, defaultLoadControl)
        val atrs = AudioAttributes.Builder().setContentType(C.CONTENT_TYPE_MUSIC).setUsage(C.USAGE_MEDIA).build()
        mExoPlayer?.setAudioAttributes(atrs)
        mExoPlayer?.addListener(PlayerEventListener())
        mExoPlayer?.addAnalyticsListener(EventLogger(trackSelector))
        try {
            mediaSource = buildMediaSource(Uri.parse(url))
            if (audioManager?.requestAudioFocus(mOnAudioFocusChangeListener_, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) { // start play
                mExoPlayer?.setPlayWhenReady(true)
            }
            mExoPlayer?.prepare(mediaSource)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*Exo Init*/
    private fun buildMediaSource(uri: Uri): MediaSource? {
        return buildMediaSource(uri, null)
    }

    private fun buildMediaSource(uri: Uri, overrideExtension: String?): MediaSource? {
        @C.ContentType val type = Util.inferContentType(uri, overrideExtension)
        return when (type) {
            C.TYPE_DASH -> DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_SS -> SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_HLS -> HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_OTHER -> ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            else -> {
                throw IllegalStateException("Unsupported type: $type")
            }
        }
    }

    private fun releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer?.release()
            mExoPlayer = null
            mediaSource = null
            trackSelector = null
        }
    }

    private fun buildDataSourceFactory(): DataSource.Factory? {
        return (application as BaseApplication).buildDataSourceFactory()
    }

    private class PlayerEventListener : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (playbackState) {
                ExoPlayer.STATE_READY -> {
                }
                ExoPlayer.STATE_BUFFERING -> {
                }
                Player.STATE_IDLE -> {
                }
                Player.STATE_ENDED -> {
                }
            }
        }

        override fun onPlayerError(e: ExoPlaybackException) {

        }
    }

}