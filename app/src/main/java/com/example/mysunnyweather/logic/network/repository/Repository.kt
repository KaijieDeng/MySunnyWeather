package com.example.mysunnyweather.logic.network.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.example.mysunnyweather.logic.Weather
import com.example.mysunnyweather.logic.dao.PlaceDao
import com.example.mysunnyweather.logic.model.Place
import com.example.mysunnyweather.logic.network.MySunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {

    fun searchPlaces(query: String) = fire(Dispatchers.IO){
        val placeResponse = MySunnyWeatherNetwork.searchPlaces(query)
        if(placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success<List<Place>>(places)
        }else{
            Result.failure<List<Place>>(RuntimeException("response status is ${placeResponse.status}"))
        }
    }


    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                MySunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                MySunnyWeatherNetwork.getDailyWeather(lng, lat)
            }

            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success<Weather>(weather)
            } else {
                Result.failure<Weather>(RuntimeException("realtime response status is ${realtimeResponse.status}"))
            }
        }
    }
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T> ) = liveData<Result<T>>(context) {
        val result = try {
            block()
        } catch (e: Exception) {
            Result.failure<T>(e)
        }
        emit(result)
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavePlace() = PlaceDao.getSavePlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

}