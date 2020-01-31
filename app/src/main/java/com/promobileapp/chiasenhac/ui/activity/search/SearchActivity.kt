package com.promobileapp.chiasenhac.ui.activity.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.adapter.SongAdapter
import com.promobileapp.chiasenhac.adapter.iLoadMore
import com.promobileapp.chiasenhac.base.BaseActivity
import com.promobileapp.chiasenhac.model.Song
import com.promobileapp.chiasenhac.ui.activity.search.presenter.SearchListener
import com.promobileapp.chiasenhac.ui.activity.search.presenter.SearchPresenter
import com.promobileapp.chiasenhac.widget.VerticalSpacesItemDecoration
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(), SearchListener, SongAdapter.OnSongClickListener,iLoadMore {

    lateinit var songAdapter: SongAdapter
    var listData: ArrayList<Song> = ArrayList()
    lateinit var layoutManager: LinearLayoutManager
    lateinit var presenter: SearchPresenter
    var pageNumber = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        init()
    }

    override fun init() {
        presenter = SearchPresenter(this, this)
        edt_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED ||
                actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.search(edt_search.text.toString())
                loadingView.visibility = View.VISIBLE
                hideKeyboard()
                true
            }
            false
        }
        layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        songAdapter = SongAdapter(this, rvSearch, layoutManager, this)
        rvSearch.setHasFixedSize(true)
        songAdapter.setLoadMore(this)
        rvSearch.layoutManager = layoutManager
        rvSearch.addItemDecoration(VerticalSpacesItemDecoration(8))
        rvSearch.adapter = songAdapter
    }

    override fun onSearchMore(listMore: ArrayList<Song>) {
        listData.clear()
        listData.addAll(listMore)
        songAdapter.addMoreData(listData)
        loadingView.visibility = View.GONE
    }

    override fun onSearchFailed() {

    }

    override fun onSearchMoreFailed() {

    }

    override fun onSearchDone(list: ArrayList<Song>) {
        listData.clear()
        listData.addAll(list)
        songAdapter.setData(listData)
        loadingView.visibility = View.GONE
    }

    override fun onLoadMore() {
        pageNumber++
        presenter.search(edt_search.text.toString(),pageNumber)
        songAdapter.showLoadMore()

    }

    override fun onSongSelected(position: Int, song: Song) {

    }
}
