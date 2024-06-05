package com.example.mysunnyweather.ui

import androidx.appcompat.app.AppCompatActivity

object ActivityManager {

    private val activities = mutableListOf<AppCompatActivity>()

    fun addActivity(activity: AppCompatActivity) {
        activities.add(activity)
    }

    fun removeActivity(activity: AppCompatActivity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
    }
}