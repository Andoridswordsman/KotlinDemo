package com.astarh.kotlindemo.ui

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astarh.kotlindemo.R
import com.astarh.kotlindemo.adapter.MeizhiAdapter
import com.astarh.kotlindemo.api.RetrofitFactory
import com.astarh.kotlindemo.bean.Meizhi
import com.astarh.kotlindemo.bean.MeizhiData
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import me.yokeyword.fragmentation.SupportFragment
import kotlinx.android.synthetic.main.fragment_meizhi.*
import org.jetbrains.anko.support.v4.toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MeizhiFragment : SupportFragment(), RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    var MeizhiAdapter: MeizhiAdapter? = null
    var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_meizhi, null)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        MeizhiAdapter = MeizhiAdapter(activity)
        recyclerView.setAdapterWithProgress(MeizhiAdapter)
        val layoutManager = StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL)
        recyclerView.setRefreshingColorResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.setRefreshListener(this)
        MeizhiAdapter?.setMore(R.layout.view_more, this)
        loadData(page)
    }

    private fun loadData(page: Int) {
        mLastVideoIndex = 0
        Observable.zip(RetrofitFactory.getInstance().getService().getShowTime(page)
                , RetrofitFactory.getInstance().getService().get休息视频Data(page)
                , BiFunction<MeizhiData, MeizhiData, MeizhiData> { meizhiData, meizhiData2 ->
            for (meizhi in meizhiData.results!!) {
                meizhi.desc = meizhi.desc + " " +
                        getFirstVideoDesc(meizhi.publishedAt, meizhiData2.results)
            }
            meizhiData
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { toast("数据缓存成功") }
                .subscribe({ meizhi: MeizhiData ->
                    if (page == 1) {
                        MeizhiAdapter?.clear()
                    }
                    MeizhiAdapter?.addAll(meizhi.results)
                }) {
                    throwable ->
                    throwable.printStackTrace()
                    toast("请求失败，请检查网络设置")
                    recyclerView.setRefreshing(false)
                }
//        RetrofitFactory.getInstance().getService().getShowTime(page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { meizhi: MeizhiData ->
//                    if(page == 1){
//                        MeizhiAdapter?.clear()
//                    }
//                    MeizhiAdapter?.addAll(meizhi.results)
//                }
    }

    private var mLastVideoIndex = 0

    private fun getFirstVideoDesc(publishedAt: String?, results: List<Meizhi>?): String? {
        var videoDesc: String? = null
        for (i in mLastVideoIndex..results!!.size - 1) {
            val video = results[i]
            if (video.publishedAt == null) video.publishedAt = video.createdAt
            if (isTheSameDay(toDate(publishedAt), toDate(video.publishedAt))) {
                videoDesc = video.desc
                mLastVideoIndex = i
                break
            }
        }
        return videoDesc
    }

    private fun isTheSameDay(one: Date?, another: Date?): Boolean {
        val _one = Calendar.getInstance()
        _one.time = one
        val _another = Calendar.getInstance()
        _another.time = another
        val oneDay = _one.get(Calendar.DAY_OF_YEAR)
        val anotherDay = _another.get(Calendar.DAY_OF_YEAR)

        return oneDay == anotherDay
    }

    private fun toDate(s: String?): Date? {
        var date: Date? = null
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            date = sdf.parse(s)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    override fun onRefresh() {
        page = 1
        loadData(page)
    }

    override fun onLoadMore() {
        ++page
        loadData(page)
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
