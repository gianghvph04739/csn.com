package com.promobileapp.chiasenhac.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promobileapp.chiasenhac.adapter.viewholder.AlbumViewHolder
import com.promobileapp.chiasenhac.adapter.viewholder.LoadingCardViewHolder
import com.promobileapp.chiasenhac.model.Album

class AlbumAdapter(
    private val context: Context?,
    private val recyclerView: RecyclerView?,
    private val gridLayoutManager: GridLayoutManager?,
    private val onClickAlbum: (Album) -> Unit

) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_LOADING = 0
    val VIEW_TYPE_ITEM = 1
    val loadingItem = Album("", "", "", "", "")
    var lstData: ArrayList<Album> = ArrayList()
    lateinit var mLoadMore: iLoadMore
    var isLoading: Boolean = false
    var visibleThreshold = 5
    var lastVisibleItem: Int = 0
    var totalItemCount: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(context)
        if (viewType == VIEW_TYPE_ITEM) {
            return AlbumViewHolder(context!!, inflate, parent)
        } else if (viewType == VIEW_TYPE_LOADING) {
            return LoadingCardViewHolder(context!!, inflate, parent)
        }
        return null!!
    }

    override fun getItemCount(): Int {
        return lstData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AlbumViewHolder) {
            var album = lstData.get(position)
            holder.bind(album)
            holder.itemView.setOnClickListener {
                onClickAlbum(album)
            }
        } else if (holder is LoadingCardViewHolder) {
            holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (lstData.get(position) == loadingItem) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun showLoadMore() {
        lstData.add(loadingItem)
        notifyItemInserted(lstData.size - 1)
    }

    fun setLoaded() {
        isLoading = false
    }

    fun setData(listData: ArrayList<Album>) {
        lstData.clear()
        lstData.addAll(listData)
        notifyDataSetChanged()
    }

    fun addMoreData(list: java.util.ArrayList<Album>) {
        val positionDelete = lstData.size - 1
        lstData.removeAt(positionDelete)
        notifyItemRemoved(positionDelete)
        lstData.addAll(list)
        notifyItemRangeChanged(positionDelete, lstData.size)
        setLoaded()
    }

    fun setLoadMore(loadmore: iLoadMore) {
        mLoadMore = loadmore
        if (gridLayoutManager != null) {
            recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    totalItemCount = gridLayoutManager.itemCount
                    lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()
                    if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                        mLoadMore.onLoadMore()
                        isLoading = true
                    }
                }
            })
        }
    }
}