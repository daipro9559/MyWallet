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
    var sharedPreferences :SharedPreferences

    @Inject
constructor(context: Context){
        sharedPreferences = context?.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)

    }

    fun putInt(key:String,value:Int){
        with(sharedPreferences.edit()){
           putInt(key,value)
            commit()
        }

    }
    fun getInt(key:String,default:Int) = sharedPreferences.getInt(key,default)

}