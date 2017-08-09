package com.astarh.kotlindemo

import android.app.Application
import com.astarh.kotlindemo.api.RetrofitFactory
import com.twobbble.tools.delegates.NotNullSingleValueVar

/**
 * Created by huangshan on 17/7/24.
 */
class App : Application() {

    companion object {
        var instance : App by NotNullSingleValueVar.DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init(){
        instance = this
    }
}