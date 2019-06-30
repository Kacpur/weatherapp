package com.example.weatherapp.data.weather_data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Coords(@SerializedName("lon") val longitude: Double,
                  @SerializedName("lat") val latitude: Double) : Serializable