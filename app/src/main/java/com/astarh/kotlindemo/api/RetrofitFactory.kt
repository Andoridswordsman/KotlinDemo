package com.astarh.kotlindemo.api

import android.content.Context
import android.util.Log
import com.astarh.kotlindemo.App
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory

/**
 * Created by huangshan on 17/7/24.
 */
class RetrofitFactory private constructor() {

    val API_BASE_URL = "http://gank.io/api/"

    val TIMEOUT: Long = 10
    private var sslSocketFactory: SSLSocketFactory? = null
    private var mRetrofit: Retrofit? = null
    private var mtimeService: MtimeService? = null

    init {
        if (mRetrofit == null) createRetrofit(App.instance)
    }

    companion object {
        fun getInstance(): RetrofitFactory {
            return Single.Instance
        }
    }

    private object Single {
        val Instance = RetrofitFactory()
    }

    private fun createRetrofit(context: Context) {
        mRetrofit = Retrofit.Builder()
                .client(constructClient(context))
                .baseUrl(API_BASE_URL)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
        mtimeService = mRetrofit?.create(MtimeService::class.java)
    }

    private fun constructClient(context: Context): OkHttpClient {
        val cacheSize: Long = 10 * 1024 * 1024
        val file = context.externalCacheDir
//        try {
//            val sslContext = SSLContext.getInstance("TLS")
//            val trustManagers = arrayOf<TrustManager>(SsX509TrustManager())
//            sslContext.init(null, trustManagers, SecureRandom())
//            sslSocketFactory = sslContext.socketFactory
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

        val client = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
//                .socketFactory(sslSocketFactory)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .cache(Cache(file, cacheSize))
                .retryOnConnectionFailure(true)
//                .addInterceptor(getInterceptor())
                .build()
        return client
    }

    fun getService(): MtimeService = mtimeService!!
}