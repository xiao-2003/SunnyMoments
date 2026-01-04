//package com.sunnymoments.android.logic.network
//
//import android.util.Log
//import com.sunnymoments.android.logic.model.PlaceResponse
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import kotlin.coroutines.resume
//import kotlin.coroutines.resumeWithException
//import kotlin.coroutines.suspendCoroutine
//
//object SunnyMomentsNetwork {
//
//    private val placeService = ServiceCreator.create<PlaceService>()
//
//    //    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query = query).await()
//    suspend fun searchPlaces(query: String): PlaceResponse {
//        val request = placeService.searchPlaces(query = query)
//        Log.d("SunnyMomentsNetwork", "Request URL: ${request.request().url()}") // 打印 URL
//        return request.await()
//    }
//
//    private suspend fun <T> Call<T>.await(): T {
//
//        return suspendCoroutine { continuation ->
//            enqueue(object : Callback<T> {
//                override fun onResponse(call: Call<T>, response: Response<T>) {
//                    val body = response.body()
//                    if (body != null) continuation.resume(body)
//                    else {
////                        continuation.resumeWithException(
////                        RuntimeException("response body is null")
////                    )
//                        val errorBody = response.errorBody()?.string()
//                        Log.e("SunnyMomentsNetwork", "response body is null, errorBody: $errorBody")
//                        continuation.resumeWithException(
//                            RuntimeException("response body is null")
//                        )
//
//                    }
//                }
//
//                override fun onFailure(call: Call<T>, t: Throwable) {
//                    continuation.resumeWithException(t)
//                }
//            })
//        }
//    }
//}

package com.sunnymoments.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.sunnymoments.android.logic.model.PlaceResponse
import android.util.Log

object SunnyMomentsNetwork {

    private val placeService = ServiceCreator.create(PlaceService::class.java)

    suspend fun searchPlaces(query: String): PlaceResponse {
        val request = placeService.searchPlaces(query = query)
        Log.d("SunnyMomentsNetwork", "Request URL: ${request.request().url}") // 打印 URL
        return request.await()
    }

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    Log.d("SunnyMomentsNetwork", "HTTP status: ${response.code()}")
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("SunnyMomentsNetwork", "response body is null, errorBody: $errorBody")
                        continuation.resumeWithException(
                            RuntimeException("response body is null")
                        )
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.e("SunnyMomentsNetwork", "Network request failed: ${t.message}")
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    suspend fun getDailyWeather(lng: Double, lat: Double) =
        weatherService.getDailyWeather(lng, lat).await()

    suspend fun getRealtimeWeather(lng: Double, lat: Double) =
        weatherService.getRealtimeWeather(lng, lat).await()

}
