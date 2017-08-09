package com.astarh.kotlindemo.adapter

import android.content.Context
import android.view.ViewGroup
import com.astarh.kotlindemo.bean.Meizhi
import com.astarh.kotlindemo.bean.MeizhiData
import com.jude.easyrecyclerview.adapter.BaseViewHolder
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter

/**
 * Created by huangshan on 17/7/26.
 */
class MeizhiAdapter(context: Context) : RecyclerArrayAdapter<Meizhi>(context) {

    override fun OnCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MeizhiHolder(parent)
    }

}