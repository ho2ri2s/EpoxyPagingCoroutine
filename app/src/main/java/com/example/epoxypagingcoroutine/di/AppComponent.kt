package com.example.epoxypagingcoroutine.di

import com.example.epoxypagingcoroutine.ui.GithubViewModel
import com.example.epoxypagingcoroutine.ui.GithubViewModelModule
import com.example.epoxypagingcoroutine.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        GithubViewModelModule::class,
        DataModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }

    fun inject(activity: MainActivity): MainActivity
}