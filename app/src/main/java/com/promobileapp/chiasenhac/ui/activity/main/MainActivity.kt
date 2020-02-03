package com.promobileapp.chiasenhac.ui.activity.main

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.adapter.PagerAdapter
import com.promobileapp.chiasenhac.base.BaseActivity
import com.promobileapp.chiasenhac.service.MusicPlayerService
import com.promobileapp.chiasenhac.ui.activity.search.SearchActivity
import com.promobileapp.chiasenhac.ui.fragment.album.AlbumFragment
import com.promobileapp.chiasenhac.ui.fragment.category.CategoryFragment
import com.promobileapp.chiasenhac.ui.fragment.chart.ChartFragment
import com.promobileapp.chiasenhac.ui.fragment.home.HomeFragment
import com.promobileapp.chiasenhac.ui.fragment.song.SongFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    var mBound = false
    var musicPlayerService: MusicPlayerService? = null
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder: MusicPlayerService.MusicServiceBinder = service as MusicPlayerService.MusicServiceBinder
            musicPlayerService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onResume() {
        super.onResume()
        bindService(connection)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindServicePlayMusic(connection, mBound)
    }

    override fun init() {
        var pagerAdapter = PagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(CategoryFragment(), getString(R.string.category))
        pagerAdapter.addFragment(AlbumFragment(), getString(R.string.home))
        pagerAdapter.addFragment(HomeFragment(), getString(R.string.home))
        pagerAdapter.addFragment(SongFragment(), getString(R.string.new_song))
        pagerAdapter.addFragment(ChartFragment(), getString(R.string.top_chart))

        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = pagerAdapter.count - 1
        mainNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_category -> {
                    viewPager.currentItem = 0
                }
                R.id.nav_newAlbum -> {
                    viewPager.currentItem = 1
                }
                R.id.nav_home -> {
                    viewPager.currentItem = 2
                }
                R.id.nav_newSong -> {
                    viewPager.currentItem = 3
                }
                R.id.nav_chart -> {
                    viewPager.currentItem = 4
                }
            }
            true
        }
        fabSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }
}
