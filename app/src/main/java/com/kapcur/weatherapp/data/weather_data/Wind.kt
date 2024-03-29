package com.example.weatherapp.data.weather_data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Wind(@SerializedName("speed") val speed: Double,
                @SerializedName("deg") val degree: Int) : Serializable