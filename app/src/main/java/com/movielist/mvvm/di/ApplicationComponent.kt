package com.movielist.mvvm.di

import com.movielist.mvvm.activity.BaseActivity
import com.movielist.mvvm.activity.MovieListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModel::class])
interface ApplicationComponent {

    fun inject(baseActivity:MovieListActivity)
}