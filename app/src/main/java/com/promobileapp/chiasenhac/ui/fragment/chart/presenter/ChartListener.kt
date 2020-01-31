package com.promobileapp.chiasenhac.ui.fragment.chart.presenter

import com.promobileapp.chiasenhac.model.Chart

interface ChartListener {
    fun onLoadChartDone(listChart: ArrayList<Chart>)
}