package com.sunnymoments.android.logic.model

import com.google.gson.annotations.SerializedName


data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String,
    val id: String? = null,           // 添加可选字段
    @SerializedName("place_id") val placeId: String? = null  // 添加place_id
)

data class PlaceResponse(
    val status: String,
    @SerializedName("places") val places: List<Place>
)

data class Location(
    @SerializedName("lng") val lng: Double,
    @SerializedName("lat") val lat: Double
)