package com.example.weatherapp.presenter

import com.example.weatherapp.R
import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.data.weather_data.WeatherPack
import com.example.weatherapp.utils.App
import com.example.weatherapp.views.MainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainPresenter(val view: MainView) {
    @Inject
    lateinit var api : WeatherApi

    fun getCurrentWeather(cityName: String) {
        view.setLoader(true)
        api.currentWeather(cityName).enqueue(object : Callback<WeatherPack> {
            override fun onResponse(call: Call<WeatherPack>, response: Response<WeatherPack>) {
                view.setLoader(false)
                response.body()?.let {
                    view.showDetailView(it)
                } ?: view.showErrorToast(App.appContext.getString(R.string.error_no_data))
            }

            override fun onFailure(call: Call<WeatherPack>, t: Throwable) {
                view.setLoader(false)
                view.showErrorToast(App.appContext.getString((R.string.error_general)))
            }
        })
    }
}