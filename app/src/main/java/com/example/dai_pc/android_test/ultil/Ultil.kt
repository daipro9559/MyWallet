package com.example.dai_pc.android_test.ultil

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


object Ultil {
    fun getTimeFromTimeStamp(timeStamp:Long):String{
        val date = Date(timeStamp * 1000)
        val sdf = SimpleDateFormat("HH:mm MM/dd/yyyy")
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
        return  sdf.format(date)
    }
}