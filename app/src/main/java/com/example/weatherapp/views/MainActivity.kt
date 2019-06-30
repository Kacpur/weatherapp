package com.example.weatherapp.views

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import com.example.weatherapp.utils.InputValidator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.view.View.FIND_VIEWS_WITH_TEXT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.example.weatherapp.R
import com.example.weatherapp.dagger.components.DaggerWeatherApiComponent
import com.example.weatherapp.dagger.modules.WeatherApiModule
import com.example.weatherapp.data.CitiesHistory
import com.example.weatherapp.data.weather_data.*
import com.example.weatherapp.presenter.MainPresenter
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    val presenter = MainPresenter(this)
    private var isConnectedToInternet: Boolean = false
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inject()
        setupBackgroundAnimation()
        setupSearchButtonClickedListener()
        setupHistory()
    }

    private fun inject() {
        DaggerWeatherApiComponent.
            builder().
            weatherApiModule(WeatherApiModule()).
            build().
            inject(presenter)
    }

    private fun setupBackgroundAnimation() {
        val animationDrawable: AnimationDrawable = main_layout.background as AnimationDrawable

        animationDrawable.setEnterFadeDuration(3750)
        animationDrawable.setExitFadeDuration(7500)

        animationDrawable.start()
    }

    private fun setupSearchButtonClickedListener() {
        search_button.setOnClickListener {
            val cityName = city_name_text.text.toString().toLowerCase()
            startSearch(cityName)
        }
    }

    private fun startSearch(cityName: String) {
        if(!isConnectedToInternet) {
            Toast.makeText(this, getString(R.string.error_no_connection), Toast.LENGTH_SHORT).show()
        } else {
            if(InputValidator.isValidCityInput(cityName)) {
                if(CitiesHistory.citiesList.contains(cityName)) {
                    removeHistoryButton(cityName)
                    CitiesHistory.removeCity(cityName)
                }
                createHistoryButton(cityName)
                CitiesHistory.addCIty(cityName)
                presenter.getCurrentWeather(cityName)
            } else {
                Toast.makeText(this, getString(R.string.error_wrong_input), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupHistory() {
        CitiesHistory.loadCities()
        history_layout.removeAllViews()
        for (city in CitiesHistory.citiesList) {
            createHistoryButton(city)
        }
    }

    private fun createHistoryButton(cityName: String) {
        val city = Button(this)
        city.text = cityName
        city.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
        city.setOnClickListener {
            city_name_text.setText(city.text.toString())
            startSearch(cityName)
        }
        history_layout.addView(city, 0)
        val params = city.layoutParams as LinearLayout.LayoutParams
        params.setMargins(10, 5, 10, 5)
        city.layoutParams = params
    }

    private fun removeHistoryButton(cityName: String) {
        val matchedButton = ArrayList<View>()
        history_layout.findViewsWithText(matchedButton, cityName, FIND_VIEWS_WITH_TEXT)
        if(matchedButton.size > 0) {
            history_layout.removeView(matchedButton[0])
        }
    }

    override fun showDetailView(weatherPack: WeatherPack) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("weatherPack", weatherPack)
        startActivity(intent)
    }

    override fun setLoader(flag: Boolean) {
        loader.visibility = if(flag) View.VISIBLE else View.GONE

        search_button.visibility = if(!flag) View.VISIBLE else View.GONE
    }

    override fun showErrorToast(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable.add(setupInternetConnectionObserver())
    }

    private fun setupInternetConnectionObserver(): Disposable {
        return ReactiveNetwork.observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {isConnected: Boolean? ->
                    isConnected?.let {
                        if(!isConnected)
                            Toast.makeText(this, getString(R.string.error_no_connection), Toast.LENGTH_SHORT).show()
                        isConnectedToInternet = isConnected
                    }
                },
                { t: Throwable? ->
                    Log.v("ReactiveNetwork", t?.message)
                }
            )
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}
