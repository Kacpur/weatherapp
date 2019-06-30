package com.example.weatherapp.dagger.components

import com.example.weatherapp.dagger.modules.WeatherApiModule
import com.example.weatherapp.presenter.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(WeatherApiModule::class))
interface WeatherApiComponent {
    fun inject(presenter: MainPresenter)
}