package com.promobileapp.chiasenhac.ui.fragment.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.adapter.CategoryVerticalAdapter
import com.promobileapp.chiasenhac.base.BaseFragment
import com.promobileapp.chiasenhac.model.Category
import com.promobileapp.chiasenhac.ui.activity.category.CategoryDetailActivity
import com.promobileapp.chiasenhac.utils.AppConstants
import com.promobileapp.chiasenhac.widget.VerticalSpacesItemDecoration
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : BaseFragment() {
    var listData: ArrayList<Category> = ArrayList()
    lateinit var categoryAdapter: CategoryVerticalAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        return view
    }

    override fun init() {
        categoryAdapter = CategoryVerticalAdapter(context, onCategoryClick = { position ->
            val intent = Intent(context, CategoryDetailActivity::class.java)
            intent.putExtra(AppConstants.CATEGORY_INTENT, listData.get(position))
            startActivity(intent)
        })

        rvCategory.setHasFixedSize(true)
        rvCategory.layoutManager = LinearLayoutManager(context)
        rvCategory.addItemDecoration(VerticalSpacesItemDecoration(8))
        rvCategory.adapter = categoryAdapter

        listData = AppConstants.getListCategory(context)
        categoryAdapter.setData(listData)
    }
}
