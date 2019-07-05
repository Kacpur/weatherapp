package com.kapcur.weatherapp.data.weather_data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Clouds(@SerializedName("all") val all: Int) : Serializable