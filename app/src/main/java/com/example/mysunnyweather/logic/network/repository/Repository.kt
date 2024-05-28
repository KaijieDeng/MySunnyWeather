package com.example.mysunnyweather.logic.network.repository

import androidx.lifecycle.liveData
import com.example.mysunnyweather.logic.Place
import com.example.mysunnyweather.logic.network.MySunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

object Repository {

    fun searchPlaces(query: String) = liveData(context = Dispatchers.IO) {
        val result = try {
            val placeResponse = MySunnyWeatherNetwork.searchPlaces(query)
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