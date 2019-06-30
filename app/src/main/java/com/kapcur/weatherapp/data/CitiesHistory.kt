package com.example.weatherapp.data

import com.example.weatherapp.utils.CacheHandler

object CitiesHistory {
        val citiesList = ArrayList<String>()

        fun addCIty(city: String) {
            citiesList.add(city)
            CacheHandler.writeListToCache(citiesList)
        }

        fun removeCity(city: String) {
            citiesList.remove(city)
            CacheHandler.writeListToCache(citiesList)
        }

        fun loadCities() {
            citiesList.clear()
            citiesList.addAll(CacheHandler.readListFromCache())
        }
}