package com.example.storyapp.utils

import android.content.Context
import com.example.storyapp.utils.UserPreferenceKey.MAP_LATITUDE
import com.example.storyapp.utils.UserPreferenceKey.MAP_LONGITUDE
import com.example.storyapp.utils.UserPreferenceKey.TOKEN
import com.example.storyapp.utils.UserPreferenceKey.USER

class SharePreferences(context: Context) {

    private val preferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE)

    fun saveToken(value: String) {
        val editor = preferences.edit()
        editor.putString(TOKEN, value)
        editor.apply()
    }

    fun getToken(): String? {
        return preferences.getString(TOKEN, "")
    }

    fun saveLatitude(value: String) {
        val editor = preferences.edit()
        editor.putString(MAP_LATITUDE, value)
        editor.apply()
    }

    fun getLatitude(): String? {
        return preferences.getString(MAP_LATITUDE, "")
    }

    fun saveLongitude(value: String) {
        val editor = preferences.edit()
        editor.putString(MAP_LONGITUDE, value)
        editor.apply()
    }

    fun getLongitude(): String? {
        return preferences.getString(MAP_LONGITUDE, "")
    }

    fun clearPreferences () {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    fun saveListString(key: String, value: List<String>) {
        val set: MutableSet<String> = HashSet()
        set.addAll(value)

        val editor = preferences.edit()
        editor.putStringSet(key, set)
        editor.apply()
    }

    fun getListString(key: String): MutableSet<String>? {
        return preferences.getStringSet(key, null)
    }

}