package com.movielist.mvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movielist.mvvm.api.MovieApi
import com.movielist.mvvm.model.Movie
import com.movielist.mvvm.model.MovieDetail
import javax.inject.Inject

class MovieRepository  @Inject constructor(private val movieApi:MovieApi){

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
    get() = _movies

    private val _movie = MutableLiveData<MovieDetail>()
    val movie: LiveData<MovieDetail>
        get() = _movie

    suspend fun getMovieList(page:Int){
        val result = movieApi.getPopularMovies(page)
        if(result.body()?.results!=null) {
            _movies.postValue(result.body()?.results)
        }
    }

    suspend fun getMovieDetail(movieId:Int){
        val result = movieApi.getMovieDetail(movieId)
        if(result.body()!=null) {
            _movie.postValue(result.body())
        }
    }


}