package com.paperplay.suteraemas.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Ahmed Yusuf on 2020-02-04.
 */
@Singleton
class PreferenceUtils @Inject constructor(var context: Context) {

//    lateinit var context: Context
    private val prefname = "com.paperplay.daggerpractice.pref"

    enum class PrefKey{
        FIREBASE_TOKEN,
        APIKEY,
        LOGIN_ROLE,
        USERNAME
    }

    fun getSharedPreferences(key: PrefKey): String? {
        return context.getSharedPreferences(prefname, MODE_PRIVATE).getString(key.toString(), "-")
    }

    fun setSharedPreferences(key: PrefKey, value: String){
        Log.d("Shared Pref", "Update key: "+key.toString()+" val: "+value)
        context.getSharedPreferences(prefname, MODE_PRIVATE).edit()
            .putString(key.toString(), value).apply()
    }

}