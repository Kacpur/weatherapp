package com.example.weatherapp.data.weather_data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SysData(@SerializedName("type") val type: Int,
                   @SerializedName("id") val id: Int,
                   @SerializedName("message") val message: Double,
                   @SerializedName("country") val country: String,
                   @SerializedName("sunrise") val sunrise: Long,
                   @SerializedName("sunset") val sunset: Long) : Serializable