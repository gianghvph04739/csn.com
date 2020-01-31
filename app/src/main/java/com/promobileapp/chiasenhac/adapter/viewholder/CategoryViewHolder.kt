package com.promobileapp.chiasenhac.adapter.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.model.Category

class CategoryViewHolder(
    private val context: Context?,
    inflater: LayoutInflater,
    parent: ViewGroup
) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_category, parent, false)) {

    private var imgThumb: ImageView? = null
    private var tvTitle: TextView? = null

    init {
        imgThumb = itemView.findViewById(R.id.imgThumb)
        tvTitle = itemView.findViewById(R.id.tv_title)
    }

    fun bind(category: Category) {
        tvTitle!!.text = category.title
        Glide.with(context!!)
            .load(category.thumbnail)
            .into(imgThumb!!)
    }
}