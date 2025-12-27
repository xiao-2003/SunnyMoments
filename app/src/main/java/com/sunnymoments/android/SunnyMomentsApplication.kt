package com.sunnymoments.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyMomentsApplication : Application() {

    companion object {

        const val TOKEN= "K6Kn9Mg0h6IvjzyR"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context // 定义了一个context变量
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext // 把 调用getApplicationContext（）方法得到的返回值赋值给context变量
    }
}