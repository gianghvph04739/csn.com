package com.promobileapp.chiasenhac.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.promobileapp.chiasenhac.adapter.viewholder.BannerViewHolder
import com.promobileapp.chiasenhac.model.Album

class BannerAdapter(
    private val context: Context,
    private val onClick: (Album) -> Unit
) :
    RecyclerView.Adapter<BannerViewHolder>() {
    var lstData: ArrayList<Album> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val inflate = LayoutInflater.from(context)
        return BannerViewHolder(context, inflate, parent)
    }

    override fun getItemCount(): Int {
        return lstData.size
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        var album = lstData.get(position)
        holder.bind(album)
        holder.itemView.setOnClickListener {
            onClick(album)
        }
    }

    fun setData(listData: ArrayList<Album>) {
        lstData.clear()
        lstData.addAll(listData)
        notifyDataSetChanged()
    }

    interface OnBannerClickListener
}