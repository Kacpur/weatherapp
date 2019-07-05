package com.kapcur.weatherapp.api

import com.kapcur.weatherapp.data.weather_data.WeatherPack
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