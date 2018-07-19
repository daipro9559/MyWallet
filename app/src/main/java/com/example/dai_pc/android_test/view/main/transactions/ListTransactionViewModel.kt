package com.example.dai_pc.android_test.view.main.transactions

import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.entity.Transaction
import com.example.dai_pc.android_test.repository.TransactionRepository
import javax.inject.Inject

class ListTransactionViewModel
@Inject
constructor(private val transactionRepository: TransactionRepository) : BaseViewModel() {
    val listTransactionLiveData = MutableLiveData<Resource<List<Transaction>>>()

    fun getAllTransaction(startBlock: Int, endBlock: Int, isShowLoading: Boolean) {
        transactionRepository.fetchTransaction(startBlock, endBlock, isShowLoading) {
        }.observeForever {
            listTransactionLiveData.value = it

        }
    }


}