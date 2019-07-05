package com.kapcur.weatherapp.utils

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception

object CacheHandler {
    val CACHE_NAME = "CitiesHistory"

    fun writeListToCache(list: ArrayList<String>) {
        val gson = Gson()
        val prefs = PreferenceManager.getDefaultSharedPreferences(App.appContext)
        val spEditor: SharedPreferences.Editor = prefs.edit()
        try {
            spEditor.putString(CACHE_NAME, gson.toJson(list))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        spEditor.commit()
    }

    fun readListFromCache() : ArrayList<String> {
        val gson = Gson()
        val prefs = PreferenceManager.getDefaultSharedPreferences(App.appContext)
        try {
            val json = prefs.getString(CACHE_NAME, null)
            if(json == null)
                return ArrayList()

            return gson.fromJson(json, object: TypeToken<ArrayList<String>>(){}.type)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return ArrayList()
    }
}