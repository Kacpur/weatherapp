package com.kapcur.weatherapp.views

import com.kapcur.weatherapp.data.weather_data.WeatherPack

interface MainView {
    fun setLoader(flag: Boolean)
    fun showDetailView(weatherPack: WeatherPack)
    fun showErrorToast(error: String)
}