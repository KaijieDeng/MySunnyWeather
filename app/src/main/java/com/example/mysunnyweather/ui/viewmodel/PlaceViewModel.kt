package com.example.mysunnyweather.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.mysunnyweather.logic.model.Place
import com.example.mysunnyweather.logic.network.repository.Repository

class PlaceViewModel: ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = searchLiveData.switchMap { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }


    fun savePlace(place: Place)  = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavePlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()



}