package com.example.dai_pc.android_test.view.main.transactions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.android.example.github.testing.OpenForTesting
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.entity.Transaction
import com.example.dai_pc.android_test.repository.TransactionRepository
import timber.log.Timber
import javax.inject.Inject

@OpenForTesting
open class ListTransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository) : BaseViewModel() {

    private val fetchParam = MutableLiveData<FetchTransactionParam>()
    val listTransactionLiveData = Transformations.switchMap(fetchParam) {
        transactionRepository.fetchTransaction(it.startBlock, it.endBlock, it.isShowLoading) {

        }
    }!!

    fun getAllTransaction(startBlock: Int, endBlock: Int, isShowLoading: Boolean) {
        fetchParam.postValue(FetchTransactionParam(startBlock, endBlock, isShowLoading))
    }
    data class FetchTransactionParam(val startBlock: Int, val endBlock: Int, val isShowLoading: Boolean)

}