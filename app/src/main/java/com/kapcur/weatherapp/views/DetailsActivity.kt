package com.kapcur.weatherapp.views

import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.kapcur.weatherapp.R
import com.kapcur.weatherapp.data.weather_data.WeatherPack
import kotlinx.android.synthetic.main.activity_details.*
import kotlin.math.abs
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {
    lateinit var weatherPack: WeatherPack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        weatherPack = intent?.extras?.getSerializable("weatherPack") as WeatherPack

        setupBacgroundAnimation()

        setupLayoutData()
    }

    private fun setupBacgroundAnimation() {
        val animationDrawable: AnimationDrawable = main_layout.background as AnimationDrawable

        animationDrawable.setEnterFadeDuration(3750)
        animationDrawable.setExitFadeDuration(7500)

        animationDrawable.start()
    }

    private fun setupLayoutData() {
        city_country_text.text = getString(R.string.details_city_and_country, weatherPack.cityName, weatherPack.sysData.country)
        val lon = weatherPack.coords.longitude
        val lat = weatherPack.coords.latitude
        val lon_abs = abs(lon)
        val lat_abs = abs(lat)
        when {
            lat >= 0 && lon >= 0 -> coords_text.text = getString(R.string.coords_n_e, lat_abs, lon_abs)
            lat >= 0 && lon < 0 -> coords_text.text = getString(R.string.coords_n_w, lat_abs, lon_abs)
            lat < 0 && lon >= 0 -> coords_text.text = getString(R.string.coords_s_e, lat_abs, lon_abs)
            lat < 0 && lon < 0 -> coords_text.text = getString(R.string.coords_s_w, lat_abs, lon_abs)
        }

        val date = Date(weatherPack.time * 1000L)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")
        sdf.timeZone = TimeZone.getDefault()
        time_text.text = sdf.format(date)

        main_text.text = weatherPack.weather[0].main
        description_text.text = weatherPack.weather[0].description

        Glide.with(this).load("http://openweathermap.org/img/w/${weatherPack.weather[0].icon}.png").into(icon_view)

        temp_min_text.text = getString(R.string.temp_degree_end, weatherPack.main.temperature_min)
        temp_text.text = getString(R.string.temp_degree_end, weatherPack.main.temperature)
        val temperature = weatherPack.main.temperature
        var tempTextColor = when {
            temperature < 10 -> Color.BLUE
            temperature > 20 -> Color.RED
            else -> Color.BLACK
        }
        temp_text.setTextColor(tempTextColor)
        temp_max_text.text = getString(R.string.temp_degree_end, weatherPack.main.temperature_max)
        pressure_text.text = getString(R.string.press_end, weatherPack.main.pressure)
        humidity_text.text = getString(R.string.percent_end, weatherPack.main.humidity)
        wind_speed_text.text = getString(R.string.speed_end, weatherPack.wind.speed)
        wind_deg_text.text = getString(R.string.degree_end, weatherPack.wind.degree)
        clouds_text.text = getString(R.string.percent_end, weatherPack.clouds.all)
    }
}
