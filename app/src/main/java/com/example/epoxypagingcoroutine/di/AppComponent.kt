package com.example.epoxypagingcoroutine.di

import com.example.epoxypagingcoroutine.ui.GithubViewModelModule
import com.example.epoxypagingcoroutine.ui.MainActivity
import dagger.Component

@Component(
    modules = [GithubViewModelModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }

    fun inject(activity: MainActivity): MainActivity
}