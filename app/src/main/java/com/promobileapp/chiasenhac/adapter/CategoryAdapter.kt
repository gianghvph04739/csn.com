package com.promobileapp.chiasenhac.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.promobileapp.chiasenhac.adapter.viewholder.CategoryViewHolder
import com.promobileapp.chiasenhac.model.Category

class CategoryAdapter(
    private val context: Context?,
    private val listData: ArrayList<Category>,
    private val onClickCategory: (Int) -> Unit
) :
    RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var inflate = LayoutInflater.from(context)
        return CategoryViewHolder(
            context,
            inflate,
            parent
        )
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        var item = listData.get(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickCategory.invoke(position)
        }
    }
}