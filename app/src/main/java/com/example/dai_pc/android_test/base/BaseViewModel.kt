package com.example.dai_pc.android_test.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context

abstract class BaseViewModel(application: Application):AndroidViewModel(application) {
    val progressState : LiveData<Boolean> = MutableLiveData<Boolean>()
}