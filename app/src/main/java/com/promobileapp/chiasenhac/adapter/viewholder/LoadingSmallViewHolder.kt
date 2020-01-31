package com.promobileapp.chiasenhac.adapter.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.promobileapp.chiasenhac.R

class LoadingSmallViewHolder(mContext: Context, inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.item_loading, parent, false
        )
    ) {

    lateinit var progressBar: ProgressBar

    init {
        progressBar = itemView.findViewById(R.id.progressBar)
    }

    fun bind() {
        progressBar.isIndeterminate = true
    }
}