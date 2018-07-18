package com.example.dai_pc.android_test.view.transaction

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.entity.TransactionSendedObject
import com.example.dai_pc.android_test.repository.TransactionRepository
import javax.inject.Inject

class CreateTransactionViewModel
@Inject
constructor(val transactionRepository: TransactionRepository)
    : BaseViewModel(){

    val transactionObjectLiveData = MutableLiveData<TransactionSendedObject>()
    val sendResult = Transformations.switchMap(transactionObjectLiveData){
        transactionRepository.sendTransaction(it,ByteArray(0))
    }


    // string of message response
    fun createTransaction(transactionSendedObject: TransactionSendedObject){
        transactionObjectLiveData.value = transactionSendedObject
    }

}