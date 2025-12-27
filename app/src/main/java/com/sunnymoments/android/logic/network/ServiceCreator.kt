package com.sunnymoments.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ServiceCreator {

    private const val BASE_URL = "https://caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL) // 设置基础请求地址
        // 作用：把 JSON ⇄ Kotlin 数据类 自动转换 :
        .addConverterFactory(GsonConverterFactory.create())
        .build() // 构建 Retrofit 对象

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}