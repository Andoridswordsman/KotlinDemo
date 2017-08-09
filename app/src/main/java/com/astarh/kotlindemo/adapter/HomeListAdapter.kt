package com.astarh.kotlindemo.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


/**
 * Created by huangshan on 17/7/26.
 */

class HomeListAdapter(fm: FragmentManager, fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

    private val titles = arrayOf("福利", "电影")
    private val fragments: ArrayList<Fragment>

    init {
        this.fragments = fragments
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment? {
        return fragments.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles.get(position)
    }
}
