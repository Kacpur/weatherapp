package com.kapcur.weatherapp.dagger.modules

import com.kapcur.weatherapp.api.WeatherApi
import com.kapcur.weatherapp.api.WeatherApiInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = arrayOf(GSONModule::class))
class WeatherApiModule {
    @Provides @Singleton
    fun provideApi(gson: Gson) : WeatherApi {
        val apiClient = OkHttpClient.Builder().addInterceptor(WeatherApiInterceptor()).build()

        return Retrofit.Builder().apply {
            baseUrl(WeatherApi.BASE_API_URL)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(apiClient)
        }.build().create(WeatherApi::class.java)
    }
}