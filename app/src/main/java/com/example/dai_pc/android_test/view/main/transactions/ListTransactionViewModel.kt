package com.example.dai_pc.android_test.view.main.transactions

import android.arch.lifecycle.MutableLiveData
import com.android.example.github.testing.OpenForTesting
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.entity.Transaction
import com.example.dai_pc.android_test.repository.TransactionRepository
import timber.log.Timber
import javax.inject.Inject

@OpenForTesting
open class ListTransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository) : BaseViewModel() {
    val listTransactionLiveData = MutableLiveData<Resource<List<Transaction>>>()

    fun getAllTransaction(startBlock: Int, endBlock: Int, isShowLoading: Boolean) {
        transactionRepository.fetchTransaction(startBlock, endBlock, isShowLoading) {
            Timber.e(it.name)
        }.observeForever {
            listTransactionLiveData.value = it

        }
    }


}