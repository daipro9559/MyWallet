package com.example.dai_pc.android_test.view.transactions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.entity.NetworkState
import com.example.dai_pc.android_test.entity.Transaction
import com.example.dai_pc.android_test.repository.TransactionRepository
import javax.inject.Inject

class ListTransactionViewModel
@Inject
constructor(private val transactionRepository: TransactionRepository) :BaseViewModel() {
    val listTransactionLiveData
    get() = transactionRepository.listTransaction

    fun getAllTransaction(address:String,startBlock : Int,endBlock :Int){
        transactionRepository.fetchTransaction(address,startBlock,endBlock)
    }

}