package com.astarh.kotlindemo.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.astarh.kotlindemo.R
import com.astarh.kotlindemo.adapter.HomeListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.fragmentation.SupportActivity

class MainActivity : SupportActivity() {

    //    private val i : Int = 1
//    private val j : Int = 1
//    private var fragments: ArrayList<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fragments = ArrayList<Fragment>()
        var MeizhiFragment = MeizhiFragment()
        var MovieFragment = MeizhiFragment()
        fragments.add(MeizhiFragment)
        fragments.add(MovieFragment)
        var adapter = HomeListAdapter(getSupportFragmentManager(), fragments)
        viewpager.setAdapter(adapter)//将adapter加入viewpager中
        //加入title选择和被选择的颜色,前面为未点击的颜色,后面为点击后的颜色
        tabLayout.setupWithViewPager(viewpager)
//        text.setText(getString(R.string.app_name))
//        toast("hello kotlin")
//        when(i){
//            j -> toast("correct")
//        }
//        Snackbar.make(window.decorView,""+transform("Blue"),Snackbar.LENGTH_LONG).setAction("测试一下，何必当真", View.OnClickListener {
//            text.setTextColor(Color.parseColor("#abdfed"))
//            text.setTextSize(30f)
//        }).show()
    }

    fun transform(color: String): Int {
        return when (color) {
            "Red" -> 0
            "Green" -> 1
            "Blue" -> 2
            else -> throw IllegalArgumentException("Invalid color param value")
        }
    }
}
