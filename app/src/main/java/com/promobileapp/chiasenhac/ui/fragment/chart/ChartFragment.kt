package com.promobileapp.chiasenhac.ui.fragment.chart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.promobileapp.chiasenhac.R
import com.promobileapp.chiasenhac.adapter.CategoryVerticalAdapter
import com.promobileapp.chiasenhac.adapter.SongAdapter
import com.promobileapp.chiasenhac.adapter.SongChartAdapter
import com.promobileapp.chiasenhac.adapter.SpinnerAdapter
import com.promobileapp.chiasenhac.base.BaseFragment
import com.promobileapp.chiasenhac.model.Chart
import com.promobileapp.chiasenhac.model.Song
import com.promobileapp.chiasenhac.ui.activity.player.PlayerActivity
import com.promobileapp.chiasenhac.ui.fragment.chart.presenter.ChartListener
import com.promobileapp.chiasenhac.ui.fragment.chart.presenter.ChartPresenter
import com.promobileapp.chiasenhac.utils.AppConstants
import com.promobileapp.chiasenhac.widget.VerticalSpacesItemDecoration
import kotlinx.android.synthetic.main.fragment_chart.*

class ChartFragment : BaseFragment(), ChartListener, SongAdapter.OnSongClickListener {
    var listData: ArrayList<Chart> = ArrayList()
    lateinit var categoryAdapter: CategoryVerticalAdapter
    lateinit var chartPresenter: ChartPresenter
    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var songAdapter: SongChartAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chart, container, false)
        return view
    }

    override fun init() {
        chartPresenter = ChartPresenter(context, this)
        chartPresenter.loadChartVietNam()
        val listNation = AppConstants.getListChart(context)

        songAdapter = SongChartAdapter(context, this)
        rvBXH.setHasFixedSize(true)
        rvBXH.layoutManager = LinearLayoutManager(context)
        rvBXH.addItemDecoration(VerticalSpacesItemDecoration(8))
        rvBXH.adapter = songAdapter

        spinnerAdapter = SpinnerAdapter(context, listNation)

        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (!listData.isEmpty()) {
                        songAdapter.setData(listData.get(p2).listSong)
                        rvBXH.scrollToPosition(0)
                    }
                }
            }
        spinner.adapter = spinnerAdapter
    }

    override fun onLoadChartDone(list: ArrayList<Chart>) {
        listData.clear()
        listData.addAll(list)
        songAdapter.setData(listData.get(0).listSong)
    }

    override fun onSongSelected(position: Int, song: Song) {
        var intent = Intent(context, PlayerActivity::class.java)
        intent.putExtra("song", song)
        startActivity(intent)
    }
}
