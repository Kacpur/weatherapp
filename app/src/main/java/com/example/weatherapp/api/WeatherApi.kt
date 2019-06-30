package com.example.weatherapp.api

import com.example.weatherapp.data.weather_data.WeatherPack
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface WeatherApi {
    @GET("weather")
    fun currentWeather(@Query("q") cityName: String) : Call<WeatherPack>

    companion object {
        val BASE_API_URL = "http://api.openweathermap.org/data/2.5/"
    }
}