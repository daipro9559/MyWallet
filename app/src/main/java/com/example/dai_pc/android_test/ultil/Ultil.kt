package com.example.dai_pc.android_test.ultil

import android.app.Activity
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.example.dai_pc.android_test.R
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

    fun showDialogNotify(activity :FragmentActivity,title:String,message:String,Callback:()->Unit){
        AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton("Ok"){dialogInterface, _ ->
                    dialogInterface.cancel()
                    Callback()
                }
                .create()
                .show()
    }
}