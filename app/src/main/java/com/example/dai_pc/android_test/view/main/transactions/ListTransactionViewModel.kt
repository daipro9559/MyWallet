package com.example.dai_pc.android_test.view.main.transactions

import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.repository.TransactionRepository
import javax.inject.Inject

class ListTransactionViewModel
@Inject
constructor(private val transactionRepository: TransactionRepository) :BaseViewModel() {
    val listTransactionLiveData
    get() = transactionRepository.listTransaction

    fun getAllTransaction(startBlock : Int,endBlock :Int){
         transactionRepository.fetchTransaction(startBlock,endBlock){
        }
    }

}