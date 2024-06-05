package com.example.mysunnyweather.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

abstract class BaseActivity<VM : ViewModel, B : ViewBinding>: AppCompatActivity() {

    abstract val viewModelClass: KClass<VM>

    lateinit var viewModel: VM
    lateinit var binding: B
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityManager.addActivity(this)
        binding = DataBindingUtil.setContentView(this,getLayoutId())
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(viewModelClass.java)){
                    return viewModelClass.java.getConstructor().newInstance() as T
                }
                return super.create(modelClass)
            }
        }).get(viewModelClass.java)
    }


    abstract fun getLayoutId(): Int


    override fun finish() {
        super.finish()
        ActivityManager.removeActivity(this)
    }
}