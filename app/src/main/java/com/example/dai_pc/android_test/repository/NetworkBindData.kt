package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.entity.NetworkState
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jnr.ffi.provider.ResultType
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class NetworkBindData<ResultType>(error: MutableLiveData<String>, Callback: (ResultType) -> Unit) {
     lateinit var flowableData : Flowable<ResultType>


    init {
        flowableData!!.let {
            it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Callback(it)
                    }, {
                        when (it) {
                            is UnknownHostException -> {
//                                networkError.postValue(NetworkState.BAD_URL)
                            }
                            is SocketTimeoutException -> {
//                                networkError.postValue(NetworkState.TIME_OUT)

                            }
                            is IOException -> {
//                                networkError.postValue(NetworkState.NO_CONENCTION)
                            }
                            else -> {

                            }

                        }
                    })
        }
    }

    fun setFlowable(flowable: Flowable<ResultType>) {
        this.flowableData = flowable
    }

}