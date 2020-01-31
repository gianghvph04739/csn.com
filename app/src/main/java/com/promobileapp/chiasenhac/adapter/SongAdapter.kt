package com.promobileapp.chiasenhac.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.promobileapp.chiasenhac.adapter.viewholder.LoadingSmallViewHolder
import com.promobileapp.chiasenhac.adapter.viewholder.SongViewHolder
import com.promobileapp.chiasenhac.model.Song

class SongAdapter(private val context: Context?, private val rvAlbum: RecyclerView?, private val layoutManager: LinearLayoutManager?, private val mListener: OnSongClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val VIEW_TYPE_LOADING = 0
    val VIEW_TYPE_ITEM = 1
    val loadingItem = Song("", "", "", "", "")
    var lstData: ArrayList<Song> = ArrayList()
    lateinit var mLoadMore: iLoadMore
    var isLoading: Boolean = false
    var visibleThreshold = 5
    var lastVisibleItem: Int = 0
    var totalItemCount: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(context)
        if (viewType == VIEW_TYPE_ITEM) {
            return SongViewHolder(context!!, inflate, parent)
        } else if (viewType == VIEW_TYPE_LOADING) {
            return LoadingSmallViewHolder(context!!, inflate, parent)
        }
        return null!!
    }

    override fun getItemCount(): Int {
        return lstData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SongViewHolder) {
            val song = lstData.get(position)
            holder.bind(song)
            holder.root?.setOnClickListener {
                Toast.makeText(context, song.title, Toast.LENGTH_LONG).show()
                mListener.onSongSelected(position, song)
            }
        } else if (holder is LoadingSmallViewHolder) {
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

    fun setData(listData: ArrayList<Song>) {
        lstData.clear()
        lstData.addAll(listData)
        notifyDataSetChanged()
    }

    fun addMoreData(list: ArrayList<Song>) {
        val positionDelete = lstData.size - 1
        lstData.removeAt(positionDelete)
        notifyItemRemoved(positionDelete)
        lstData.addAll(list)
        notifyItemRangeChanged(positionDelete, lstData.size)
        setLoaded()
    }

    fun setLoadMore(loadmore: iLoadMore) {
        mLoadMore = loadmore
        rvAlbum?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = layoutManager?.itemCount!!
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    mLoadMore.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

    interface OnSongClickListener {
        fun onSongSelected(position: Int, song: Song)
    }
}