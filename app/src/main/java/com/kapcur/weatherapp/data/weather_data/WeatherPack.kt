package com.kapcur.weatherapp.data.weather_data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherPack(@SerializedName("coord") val coords: Coords,
                       @SerializedName("weather") val weather: List<Weather>,
                       @SerializedName("base") val base: String,
                       @SerializedName("main") val main: MainData,
                       @SerializedName("visibility") val visibility: Int,
                       @SerializedName("wind") val wind: Wind,
                       @SerializedName("clouds") val clouds: Clouds,
                       @SerializedName("dt") val time: Long,
                       @SerializedName("sys") val sysData: SysData,
                       @SerializedName("timezone") val timezone: Long,
                       @SerializedName("id") val id: Long,
                       @SerializedName("name") val cityName: String,
                       @SerializedName("cod") val code: Int) : Serializable