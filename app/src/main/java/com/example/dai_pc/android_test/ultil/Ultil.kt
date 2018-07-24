package com.example.dai_pc.android_test.ultil

import android.app.Activity
import android.content.Context
import android.support.design.widget.TextInputEditText
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import com.example.dai_pc.android_test.R
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


object Ultil {
    fun getTimeFromTimeStamp(timeStamp: Long): String {
        val date = Date(timeStamp * 1000)
        val sdf = SimpleDateFormat("HH:mm MM/dd/yyyy")
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        return sdf.format(date)
    }

    fun getTimeFromTimeStamp(timeStamp: Long, context: Context): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timeStamp * 1000
        return DateFormat.getLongDateFormat(context).format(calendar.time)
    }

    fun showDialogNotify(activity: FragmentActivity, title: String, message: String, Callback: () -> Unit) {
        AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(activity.getString(R.string.ok)) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    Callback()
                }
                .create()
                .show()
    }

    fun showDialogInputPassword(activity: FragmentActivity, title: String, textButtonOk: String, Callback: (String) -> Unit) {
        var dialog: AlertDialog? = null
        dialog = AlertDialog.Builder(activity)
                .setTitle(title)
                .setView(R.layout.dialog_create_wallet)
                .setPositiveButton(textButtonOk) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    val editText = dialog!!.findViewById<TextInputEditText>(R.id.edt_pass)
                    Callback(editText!!.text.toString())
                }
                .setNegativeButton(R.string.cancel) { dialogInterface, _ ->
                    dialogInterface.cancel()
                }
                .create()
        dialog.show()
    }
}