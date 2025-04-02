package com.esthiti.horoscopeandroid.utils

import android.content.Context

class SessionManager(context: Context) {

    private val sharedPref = context.getSharedPreferences("zodiak_session", Context.MODE_PRIVATE)

    fun setFavoriteHoroscope(id: String){
        val editor = sharedPref.edit()
        editor.putString("FAVORITE_HOROSCOPE", id)
        editor.apply()
    }

    fun getFavoriteHoroscope():String{
        return sharedPref.getString("FAVORITE_HOROSCOPE", "")!!
    }
}