package com.example.mysunnyweather.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MySunnyWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val TOKEN = "tHVeP5qSUGJn2Uft"
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}