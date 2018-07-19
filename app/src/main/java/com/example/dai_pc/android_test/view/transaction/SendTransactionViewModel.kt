package com.example.dai_pc.android_test.view.transaction

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.entity.TransactionSendObject
import com.example.dai_pc.android_test.repository.BalanceRepository
import com.example.dai_pc.android_test.repository.TransactionRepository
import javax.inject.Inject

class SendTransactionViewModel
@Inject
constructor(val transactionRepository: TransactionRepository,
            val balanceRepository: BalanceRepository)
    : BaseViewModel(){


    val transactionObjectLiveData = MutableLiveData<TransactionSendObject>()
    val sendResult = Transformations.switchMap(transactionObjectLiveData){
        transactionRepository.sendTransaction(it,ByteArray(0))
    }

    // string of message response
    fun createTransaction(transactionSendObject: TransactionSendObject){
        transactionObjectLiveData.value = transactionSendObject
    }


}