//package com.sunnymoments.android.logic.network
//
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.create
//
//object ServiceCreator {
//
//    private const val BASE_URL = "https://caiyunapp.com/"
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL) // 设置基础请求地址
//        // 作用：把 JSON ⇄ Kotlin 数据类 自动转换 :
//        .addConverterFactory(GsonConverterFactory.create())
//        .build() // 构建 Retrofit 对象
//
//    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
//
//    inline fun <reified T> create(): T = create(T::class.java)
//}

package com.sunnymoments.android.logic.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36"
                )
                .build()
            chain.proceed(request)
        }
        // 可选：打印请求日志
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}
