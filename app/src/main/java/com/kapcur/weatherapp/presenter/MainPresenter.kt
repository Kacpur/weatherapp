package com.kapcur.weatherapp.presenter

import com.kapcur.weatherapp.R
import com.kapcur.weatherapp.api.WeatherApi
import com.kapcur.weatherapp.data.weather_data.WeatherPack
import com.kapcur.weatherapp.utils.App
import com.kapcur.weatherapp.views.MainView
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