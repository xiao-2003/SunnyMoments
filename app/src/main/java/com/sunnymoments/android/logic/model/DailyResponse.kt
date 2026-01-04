package com.sunnymoments.android.logic.model

import com.google.gson.annotations.SerializedName

data class DailyResponse(val status: String, val result: Result) {

    data class LifeDescription(val date: String, val index: String, val desc: String)

    data class LifeIndex(
        val ultraviolet: List<LifeDescription>,
        val carWashing: List<LifeDescription>,
        val dressing: List<LifeDescription>,
        val coldRisk: List<LifeDescription>
    )

    data class Skycon(val value: String, val date: String)

    data class Temperature(val date: String, val max: Float, val min: Float, val avg: Float)

    data class Daily(
        val temperature: List<Temperature>,
        val skycon: List<Skycon>,
        @SerializedName("life_index") val lifeIndex: LifeIndex
    )

    data class Result(val daily: Daily)

}