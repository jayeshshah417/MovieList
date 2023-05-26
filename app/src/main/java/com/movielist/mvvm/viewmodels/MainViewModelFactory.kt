package com.movielist.mvvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movielist.mvvm.repository.MovieRepository
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val repository: MovieRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}