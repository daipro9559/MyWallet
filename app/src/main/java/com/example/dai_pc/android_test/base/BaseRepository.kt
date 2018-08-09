package com.example.dai_pc.android_test.base

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.dai_pc.android_test.R
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseRepository(val context: Context) {
    val error = MutableLiveData<String>()

    protected fun setError(throwable: Throwable) {
        when (throwable) {
//            is IOException -> {
//
//            }
//            is UnknownHostException -> {
//
//            }
            is SocketTimeoutException -> {
                error.value = context.getString(R.string.socket_timeout)
            }

            is HttpException ->{
                val ex = throwable as HttpException
                error.value = ex.message()
            }
            else -> {
                error.value = throwable.message
            }
        }
    }
}