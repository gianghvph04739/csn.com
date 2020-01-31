package com.promobileapp.chiasenhac.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.promobileapp.chiasenhac.adapter.viewholder.CategoryViewHolder
import com.promobileapp.chiasenhac.model.Category

class CategoryVerticalAdapter(
    private val context: Context?,
    private val onCategoryClick: (Int) -> Unit
) :
    RecyclerView.Adapter<CategoryViewHolder>() {
    var lstData: ArrayList<Category> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflate = LayoutInflater.from(context)
        return CategoryViewHolder(context!!, inflate, parent)
    }

    override fun getItemCount(): Int {
        return lstData.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        var album = lstData.get(position)
        holder.bind(album)
        holder.itemView.setOnClickListener {
            onCategoryClick.invoke(position)
        }
    }

    fun setData(listData: ArrayList<Category>) {
        lstData.clear()
        lstData.addAll(listData)
        notifyDataSetChanged()
    }
}