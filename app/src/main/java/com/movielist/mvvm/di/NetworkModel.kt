package com.movielist.mvvm.di

import com.movielist.mvvm.api.MovieApi
import com.movielist.mvvm.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModel {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(object : Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request().newBuilder()
                    .addHeader("Authorization","Bearer "+Constants.TOKEN)
                    .build()
                return chain.proceed(request)
            }
        }).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(clientval:OkHttpClient):Retrofit{
        return Retrofit.Builder().client(clientval).baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideMovieApi(retrofit:Retrofit):MovieApi{
        return retrofit.create(MovieApi::class.java)
    }
}