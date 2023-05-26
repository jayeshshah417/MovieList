package com.movielist.mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movielist.mvvm.model.Movie
import com.movielist.mvvm.model.MovieDetail
import com.movielist.mvvm.repository.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel(val repository: MovieRepository):ViewModel()
{
    val movieLivedata : LiveData<List<Movie>>
    get() = repository.movies

    val movieDetailLivedata : LiveData<MovieDetail>
        get() = repository.movie

    init {
        viewModelScope.launch {
            repository.getMovieList(1)
        }
    }

    fun getMovies(page:Int){
        viewModelScope.launch {
            repository.getMovieList(page)
        }
    }

    fun getMovie(movieId:Int){
        viewModelScope.launch {
            repository.getMovieDetail(movieId)
        }
    }
}