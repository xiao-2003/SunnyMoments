package com.sunnymoments.android.logic.network

import com.sunnymoments.android.SunnyMomentsApplication
import com.sunnymoments.android.logic.model.PlaceResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface PlaceService {
    @GET("v2/place?token=${SunnyMomentsApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}