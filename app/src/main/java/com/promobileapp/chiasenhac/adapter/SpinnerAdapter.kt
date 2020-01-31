package com.promobileapp.chiasenhac.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.promobileapp.chiasenhac.R

class SpinnerAdapter(
    private val context: Context?,
    private val listData: List<String>
) :
    BaseAdapter() {

    lateinit var inflater: LayoutInflater

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_spinner, p2, false)
        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        tvTitle.text = listData[p0]
        return view
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return listData.size
    }
}