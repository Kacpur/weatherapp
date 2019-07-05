package com.kapcur.weatherapp.data.weather_data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MainData(@SerializedName("temp") val temperature: Double,
                    @SerializedName("pressure") val pressure: Int,
                    @SerializedName("humidity") val humidity: Int,
                    @SerializedName("temp_min") val temperature_min: Double,
                    @SerializedName("temp_max") val temperature_max: Double) : Serializable