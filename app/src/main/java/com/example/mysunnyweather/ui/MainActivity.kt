package com.example.mysunnyweather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mysunnyweather.R
import com.example.mysunnyweather.databinding.ActivityMainBinding
import com.example.mysunnyweather.ui.viewmodel.MainViewModel
import kotlin.reflect.KClass

class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>() {
    override val viewModelClass: KClass<MainViewModel>
        get() = MainViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}