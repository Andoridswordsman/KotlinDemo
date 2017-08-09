package com.astarh.kotlindemo.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.astarh.kotlindemo.R
import com.astarh.kotlindemo.RatioImageView
import com.astarh.kotlindemo.bean.Meizhi
import com.astarh.kotlindemo.bean.MeizhiData
import com.bumptech.glide.Glide
import com.jude.easyrecyclerview.adapter.BaseViewHolder

/**
 * Created by huangshan on 17/7/26.
 */

class MeizhiHolder(parent: ViewGroup) : BaseViewHolder<Meizhi>(parent,R.layout.item_meizhi) {

    private var meizhi: RatioImageView
    private var title: TextView

    init {
        meizhi = `$`<RatioImageView>(R.id.meizhi)
        meizhi.setOriginalSize(50,50)
        title = `$`<TextView>(R.id.title)
    }

    override fun setData(data: Meizhi) {
        super.setData(data)
        Glide.with(context).load(data.url).centerCrop().into(meizhi)
        title.setText(data.desc)
    }
}
