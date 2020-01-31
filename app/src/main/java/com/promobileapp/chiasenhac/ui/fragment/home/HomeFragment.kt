package com.promobileapp.chiasenhac.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.promobileapp.chiasenhac.adapter.AlbumAdapter
import com.promobileapp.chiasenhac.adapter.BannerAdapter
import com.promobileapp.chiasenhac.adapter.CategoryAdapter
import com.promobileapp.chiasenhac.base.BaseFragment
import com.promobileapp.chiasenhac.model.Album
import com.promobileapp.chiasenhac.model.Category
import com.promobileapp.chiasenhac.ui.activity.list.ListActivity
import com.promobileapp.chiasenhac.ui.fragment.home.presenter.HomeListener
import com.promobileapp.chiasenhac.ui.fragment.home.presenter.HomePresenter
import com.promobileapp.chiasenhac.utils.AppConstants
import com.promobileapp.chiasenhac.widget.GridSpacesItemDecoration
import com.promobileapp.chiasenhac.widget.HorizontalSpacesItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), HomeListener, BannerAdapter.OnBannerClickListener {
    lateinit var presenter: HomePresenter
    lateinit var bannerAdapter: BannerAdapter
    lateinit var nationAdapter: CategoryAdapter
    lateinit var listNation: ArrayList<Category>
    lateinit var albumAdapter: AlbumAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.promobileapp.chiasenhac.R.layout.fragment_home, container, false)
        return view
    }

    override fun init() {
        presenter = HomePresenter(context!!, this)
        initChart()
        initBanner()
        initNewAlbum()
        presenter.loadBanner()
        presenter.loadNewAlbum()
    }

    fun initNewAlbum() {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        albumAdapter = AlbumAdapter(context, home_rv_newAlbum, null, onClickAlbum = { album ->
            val intent = Intent(context, ListActivity::class.java)
            intent.putExtra(AppConstants.ALBUM_INTENT, album)
            startActivity(intent)
        })
        home_rv_newAlbum.setHasFixedSize(true)
        home_rv_newAlbum.addItemDecoration(HorizontalSpacesItemDecoration(8))
        home_rv_newAlbum.layoutManager = linearLayoutManager
        home_rv_newAlbum.addOnScrollListener(CenterScrollListener())
        home_rv_newAlbum.adapter = albumAdapter
    }

    fun initBanner() {
        bannerAdapter = BannerAdapter(context!!, onClick = { album ->
            val intent = Intent(context, ListActivity::class.java)
            intent.putExtra(AppConstants.ALBUM_INTENT, album)
            startActivity(intent)
        })
        home_rv_Banner.setHasFixedSize(true)
        var bannerLayoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)
        bannerLayoutManager.maxVisibleItems = 1
        bannerLayoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())
        home_rv_Banner.layoutManager = bannerLayoutManager
        home_rv_Banner.addOnScrollListener(CenterScrollListener())
        home_rv_Banner.adapter = bannerAdapter
    }

    fun initChart() {
        listNation = AppConstants.getListNation(context!!)
        nationAdapter = CategoryAdapter(context, listNation, onClickCategory = { position ->
            val intent = Intent(context, ListActivity::class.java)
            intent.putExtra(AppConstants.CATEGORY_INTENT, listNation.get(position))
            startActivity(intent)
        })
        rv_Chart.setHasFixedSize(true)
        rv_Chart.layoutManager = GridLayoutManager(context, 2)
        rv_Chart.addItemDecoration(GridSpacesItemDecoration(8))
        rv_Chart.adapter = nationAdapter
    }

    override fun onLoadBannerSuccessFull(listBanner: ArrayList<Album>) {
        bannerAdapter.setData(listBanner)
    }

    override fun onLoadNewAlbumSuccessFull(listNewAlbum: ArrayList<Album>) {
        albumAdapter.setData(listNewAlbum)
    }
}

