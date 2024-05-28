package com.example.mysunnyweather.ui.weather

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mysunnyweather.R
import com.example.mysunnyweather.databinding.ActivityWeatherBinding
import com.example.mysunnyweather.databinding.ForecastItemBinding
import com.example.mysunnyweather.logic.Weather
import com.example.mysunnyweather.logic.model.getSky
import com.example.mysunnyweather.ui.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding

    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)

        if (viewModel.locationLng.isEmpty()){
            viewModel.locationLng = intent.getStringExtra("location_lng")?:""
        }

        if (viewModel.locationLat.isEmpty()){
            viewModel.locationLat = intent.getStringExtra("location_lat")?:""
        }

        if (viewModel.placeName.isEmpty()){
            viewModel.placeName = intent.getStringExtra("place_name")?:""
        }

        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null){
                showWeatherInfo(weather)
            }else{
                Toast.makeText(this,"无法成功获取天气信息,",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        viewModel.refreshWeather(viewModel.locationLng,viewModel.locationLat)
    }

    private fun showWeatherInfo(weather: Weather){
        binding.nowLayout.placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        val currentTempText = "${realtime.temperature.toInt()}°C"
        binding.nowLayout.currentTemp.text = currentTempText
        binding.nowLayout.currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        binding.nowLayout.currentAQI.text = currentPM25Text
        binding.nowLayout.nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        // 填充forecast布局
        binding.forecastLayout.forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days){
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val forecastBinding = DataBindingUtil.inflate<ForecastItemBinding>(layoutInflater,R.layout.forecast_item,binding.forecastLayout.forecastLayout,false)

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            forecastBinding.dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            forecastBinding.skyIcon.setImageResource(sky.icon)
            forecastBinding.skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} °C"
            forecastBinding.temperatureInfo.text = tempText
            binding.forecastLayout.forecastLayout.addView(forecastBinding.root)
        }

        val lifeIndex = daily.lifeIndex
        binding.lifeIndexLayout.coldRiskText.text = lifeIndex.coldRisk[0].desc
        binding.lifeIndexLayout.dressingText.text = lifeIndex.dressing[0].desc
        binding.lifeIndexLayout.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        binding.lifeIndexLayout.carWashingText.text = lifeIndex.carWashing[0].desc
        binding.lifeIndexLayout.carWashingText.text = lifeIndex.carWashing[0].desc
        binding.weatherLayout.visibility = View.VISIBLE
    }

}