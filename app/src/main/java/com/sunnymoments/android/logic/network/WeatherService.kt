package com.sunnymoments.android.logic.network

import com.sunnymoments.android.SunnyMomentsApplication
import com.sunnymoments.android.logic.model.DailyResponse
import com.sunnymoments.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {
    @GET("v2.5/${SunnyMomentsApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(
        @Path("lng") lng: Double, @Path("lat") lat: Double
    ): Call<RealtimeResponse>

    @GET("v2.5/${SunnyMomentsApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: Double, @Path("lat") lat: Double): Call<DailyResponse>
}