package com.sunnymoments.android.logic.model

import com.google.gson.annotations.SerializedName

data class RealtimeResponse(val status: String, val result: Result) {

    data class AQI(val chn: Int, val usa: Int)
    data class AirQuality(val aqi: AQI)

    data class Realtime(
        val status: String,
        val temperature: Float,
        val skycon: String,
        @SerializedName("air_quality") val airQuality: AirQuality,
        @SerializedName("life_index") val lifeIndex: DailyResponse.LifeIndex
    )


    data class Result(val realtime: Realtime)

    data class LifeIndex(
        val ultraviolet: LifeItem,
        val comfort: LifeItem
    )

    data class LifeItem(
        val index: Int,
        val desc: String
    )
}