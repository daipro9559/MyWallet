package com.example.dai_pc.android_test.base

import android.arch.lifecycle.MutableLiveData

abstract class BaseRepository {
    val error = MutableLiveData<String>()

    protected fun setError(string: String){
        error.postValue(string)
    }
}