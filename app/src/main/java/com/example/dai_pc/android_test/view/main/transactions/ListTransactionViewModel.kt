package com.example.dai_pc.android_test.view.main.transactions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.android.example.github.testing.OpenForTesting
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.repository.TransactionRepository
import com.example.dai_pc.android_test.repository.TransactionStellarRepo
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import javax.inject.Inject

@OpenForTesting
open class ListTransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository,
                                                        private val transactionStellarRepo: TransactionStellarRepo,
                                                        private val preferenceHelper: PreferenceHelper) : BaseViewModel() {
    private var platform = preferenceHelper.getPlatform()
    private val fetchParamEth = MutableLiveData<FetchTransactionParam>()
    private val fetchParamStellar = MutableLiveData<FetchTransactionParam>()
    init {
        errorLiveData = transactionRepository.error
        getAllTransaction()
    }
    val listTransactionEther = Transformations.switchMap(fetchParamEth) {
        transactionRepository.fetchTransaction(it.startBlock, it.endBlock, it.isShowLoading)
    }!!
    val listTransactionStellar = Transformations.switchMap(fetchParamStellar) {
        transactionStellarRepo.fetchAllTransaction()
    }!!


    fun getAllTransaction() {
        if (platform == Constant.ETHEREUM_PLATFORM) {
            fetchParamEth.postValue(FetchTransactionParam(0, 99999999, true))
        }else if (platform == Constant.STELLAR_PLATFORM){
            fetchParamStellar.value = FetchTransactionParam()
        }
    }

    data class FetchTransactionParam(val startBlock: Int, val endBlock: Int, val isShowLoading: Boolean){
        constructor() :this(0,0,false)
    }



}