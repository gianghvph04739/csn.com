package com.promobileapp.chiasenhac.ui.activity.category

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.adapter.AlbumAdapter
import com.promobileapp.chiasenhac.base.BaseActivity
import com.promobileapp.chiasenhac.model.Album
import com.promobileapp.chiasenhac.model.Category
import com.promobileapp.chiasenhac.ui.activity.category.presenter.CategoryDetailListener
import com.promobileapp.chiasenhac.ui.activity.category.presenter.CategoryDetailPresenter
import com.promobileapp.chiasenhac.ui.activity.list.ListActivity
import com.promobileapp.chiasenhac.utils.AppConstants
import com.promobileapp.chiasenhac.widget.GridSpacesItemDecoration
import kotlinx.android.synthetic.main.activity_category_detail.*

class CategoryDetailActivity : BaseActivity(), CategoryDetailListener {
    lateinit var presenter: CategoryDetailPresenter
    lateinit var albumAdapter: AlbumAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detail)
        init()
    }

    override fun init() {
        val category = intent.getParcelableExtra<Category>(AppConstants.CATEGORY_INTENT)
        toolBar.title = category.title

        toolBar.setNavigationOnClickListener({
            onBackPressed()
        })

        loadingView.visibility = View.VISIBLE
        albumAdapter = AlbumAdapter(this, null, null, onClickAlbum = { album ->
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra(AppConstants.ALBUM_INTENT, album)
            startActivity(intent)
        })
        rv_DetailCategory.setHasFixedSize(true)
        rv_DetailCategory.addItemDecoration(GridSpacesItemDecoration(8))
        rv_DetailCategory.layoutManager = GridLayoutManager(this, 2)
        rv_DetailCategory.adapter = albumAdapter

        presenter = CategoryDetailPresenter(this, this)
        presenter.getListDetail(category.url)


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onLoadListDetailDone(listAlbum: ArrayList<Album>) {
        loadingView.visibility = View.GONE
        albumAdapter.setData(listAlbum)
    }

    override fun onLoadListDetailFaied() {
        loadingView.visibility = View.GONE
    }
}
