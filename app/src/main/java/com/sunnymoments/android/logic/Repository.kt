package com.sunnymoments.android.logic

import androidx.lifecycle.liveData
import com.sunnymoments.android.logic.model.Place
import com.sunnymoments.android.logic.network.SunnyMomentsNetwork
import kotlinx.coroutines.Dispatchers
import retrofit2.http.Query

object Repository {

    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyMomentsNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}