package com.example.weatherapp.views

import com.example.weatherapp.data.weather_data.WeatherPack

interface MainView {
    fun setLoader(flag: Boolean)
    fun showDetailView(weatherPack: WeatherPack)
    fun showErrorToast(error: String)
}