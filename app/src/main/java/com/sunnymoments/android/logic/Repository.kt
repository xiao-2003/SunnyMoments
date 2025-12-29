package com.sunnymoments.android.logic

import com.google.gson.Gson
import android.util.Log
import androidx.lifecycle.liveData
import com.sunnymoments.android.logic.model.Place
import com.sunnymoments.android.logic.network.SunnyMomentsNetwork
import kotlinx.coroutines.Dispatchers
import retrofit2.http.Query

object Repository {


//    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
//
//        val placeResponse = SunnyMomentsNetwork.searchPlaces(query)
//        Log.d("PlaceRepo", placeResponse.toString())
//
//
//        val result = try {
//            val placeResponse = SunnyMomentsNetwork.searchPlaces(query)
//            if (placeResponse.status.equals("ok", ignoreCase = true)) {
//                val places = placeResponse.places
//                Result.success(places)
//            } else {
//                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
//            }
////        } catch (e: Exception) {
////            Result.failure<List<Place>>(e)
////        }
//        } catch (e: Exception) {
//            e.printStackTrace()   // ★ 必须加
//            Result.failure<List<Place>>(e)
//        }
//        emit(result)
//    }
//}


    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyMomentsNetwork.searchPlaces(query)

            // 打印完整 JSON
            val gson = Gson()
            val jsonStr = gson.toJson(placeResponse)
            Log.d("PlaceRepo", "Raw response JSON: $jsonStr")

            if (placeResponse.status.equals("ok", ignoreCase = true)) {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}