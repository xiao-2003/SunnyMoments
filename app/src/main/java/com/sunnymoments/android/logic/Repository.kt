package com.sunnymoments.android.logic

import com.google.gson.Gson
import android.util.Log
import androidx.lifecycle.liveData
import com.sunnymoments.android.logic.model.Weather
import com.sunnymoments.android.logic.network.SunnyMomentsNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

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


    fun searchPlaces(query: String) = fire(Dispatchers.IO) {

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
    }


    fun refreshWeather(lng: Double, lat: Double) = fire(Dispatchers.IO) {
        coroutineScope {
            try {


                val deferredRealtime = async {
                    SunnyMomentsNetwork.getRealtimeWeather(lng, lat)
                }
                val deferredDaily = async {
                    SunnyMomentsNetwork.getDailyWeather(lng, lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()


                Log.d("WeatherRepo", "realtime=${Gson().toJson(realtimeResponse)}")
                Log.d("WeatherRepo", "daily=${Gson().toJson(dailyResponse)}")


                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    val weather =
                        Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                    Result.success(weather)
                } else {
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}" +
                                    "daily response status is ${dailyResponse.status}"
                        )
                    )
                    // Result.failure(RuntimeException("weather response error"))
                }
            } catch (e: Exception) {
                Log.e("WeatherRepo", "Network request failed", e)
                Result.failure(e)
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}