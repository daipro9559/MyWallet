package com.example.dai_pc.android_test.ultil

import android.content.Context
import android.content.SharedPreferences
import android.preference.Preference
import android.support.v4.content.SharedPreferencesCompat
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.Constant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceHelper {
    var sharedPreferences: SharedPreferences

    @Inject
    constructor(context: Context) {
        sharedPreferences = context?.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    }

    fun putInt(key: String, value: Int) {
        with(sharedPreferences.edit()) {
            putInt(key, value)
            commit()
        }

    }

    fun getInt(key: String, default: Int) = sharedPreferences.getInt(key, default)

    fun putString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            commit()
        }
    }

    fun getString(key: String) = sharedPreferences.getString(key, null)

    fun getBoolean(key: String) = sharedPreferences.getBoolean(key, false)

    fun putBoolean(key: String, value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            commit()
        }
    }

    fun getPlatform(): String {
        return sharedPreferences.getString(Constant.PLATFORM_KEY, Constant.STELLAR_PLATFORM)
    }


}