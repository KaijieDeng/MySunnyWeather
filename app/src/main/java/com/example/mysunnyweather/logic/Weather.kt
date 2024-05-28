package com.example.mysunnyweather.logic

import com.example.mysunnyweather.logic.model.DailyResponse
import com.example.mysunnyweather.logic.model.RealtimeResponse

data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)
