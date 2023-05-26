package com.movielist.mvvm

import android.app.Application
import com.movielist.mvvm.di.ApplicationComponent
import com.movielist.mvvm.di.DaggerApplicationComponent

class MovieApplication :Application() {
    lateinit var applicationComponent:ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder().build()
    }
}