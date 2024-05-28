package com.example.mysunnyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.mysunnyweather.app.MySunnyWeatherApplication
import com.example.mysunnyweather.logic.model.Place
import com.google.gson.Gson

object PlaceDao {

    private const val PLACE = "place"
    fun savePlace(place: Place) {
        sharePreferences().edit {
            putString(PLACE,Gson().toJson(place))
        }
    }

    fun getSavePlace(): Place{
        val placeJson = sharePreferences().getString(PLACE,"")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    fun isPlaceSaved() = sharePreferences().contains(PLACE)

    private fun sharePreferences() = MySunnyWeatherApplication.context.getSharedPreferences("sunny_weather",Context.MODE_PRIVATE)
}