package com.movielist.mvvm.api

import com.movielist.mvvm.model.MovieDetail
import com.movielist.mvvm.model.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page:Int): Response<MovieList>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movie_id:Int): Response<MovieDetail>
}