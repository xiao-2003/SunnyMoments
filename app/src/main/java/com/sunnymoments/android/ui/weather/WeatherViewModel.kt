package com.sunnymoments.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.sunnymoments.android.logic.Repository
import com.sunnymoments.android.logic.model.Location

class WeatherViewModel : ViewModel() {
    var locationLng = ""
    var locationLat = ""
    var placeName = ""
    private val locationLiveData = MutableLiveData<Location>()

    val weatherLiveData = locationLiveData.switchMap { location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng.toDouble(), lat.toDouble())
    }
}