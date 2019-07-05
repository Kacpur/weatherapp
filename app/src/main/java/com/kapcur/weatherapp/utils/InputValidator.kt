package com.kapcur.weatherapp.utils

import java.util.regex.Pattern

object InputValidator {
    // Following pattern matches only one-word, letters-only strings
    private val VALID_CITY_REGEX = Pattern.compile("[a-zA-Z]+")

    fun isValidCityInput(city: String) = VALID_CITY_REGEX.matcher(city).matches()
}