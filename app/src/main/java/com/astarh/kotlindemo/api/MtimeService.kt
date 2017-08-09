package com.astarh.kotlindemo.api

import com.astarh.kotlindemo.bean.MeizhiData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by huangshan on 17/7/24.
 */
interface MtimeService {

    @GET("data/福利/10/{page}")
    fun getShowTime(@Path("page") page: Int): Observable<MeizhiData>

    @GET("data/休息视频/10/{page}")
    fun get休息视频Data(@Path("page") page: Int): Observable<MeizhiData>
}