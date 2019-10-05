package com.example.epoxypagingcoroutine

import android.app.Application
import com.example.epoxypagingcoroutine.di.AppComponent
import com.example.epoxypagingcoroutine.di.DaggerAppComponent

class App: Application(){

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }
}