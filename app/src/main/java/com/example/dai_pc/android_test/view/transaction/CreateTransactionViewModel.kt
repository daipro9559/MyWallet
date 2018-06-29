package com.example.dai_pc.android_test.view.transaction

import android.app.Application
import android.arch.lifecycle.LiveData
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.entity.TransactionSendedObject
import com.example.dai_pc.android_test.repository.TransactionRepository
import javax.inject.Inject

class CreateTransactionViewModel
@Inject
constructor(application: Application,
            transactionRepository: TransactionRepository)
    : BaseViewModel(application){

    // string of message response
    fun createTransaction(transactionSendedObject: TransactionSendedObject):LiveData<String>{

    }

}