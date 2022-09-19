package com.example.storyapp.utils

import android.content.Context
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