package com.example.dai_pc.android_test.view.main.transactions

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import com.android.example.github.testing.OpenForTesting
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.repository.BalanceRepository
import com.example.dai_pc.android_test.repository.TransactionRepository
import com.example.dai_pc.android_test.repository.TransactionStellarRepo
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import javax.inject.Inject

@OpenForTesting
open class ListTransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository,
                                                        private val transactionStellarRepo: TransactionStellarRepo,
                                                        private val preferenceHelper: PreferenceHelper,
                                                        private val balanceRepository: BalanceRepository,
                                                        private val context : Context) : BaseViewModel() {
    private var platform = preferenceHelper.getPlatform()
    private val fetchParamEth = MutableLiveData<FetchTransactionParam>()
    private val fetchParamStellar = MutableLiveData<FetchTransactionParam>()
    val balanceEther = balanceRepository.balanceEther
    val balanceStellar = balanceRepository.balanceStellar
    val accountLiveData = MutableLiveData<String>()
    init {
        getAccount()
        errorLiveData = transactionRepository.error
    }
    val listTransactionEther = Transformations.switchMap(fetchParamEth) {
        transactionRepository.fetchTransaction(it.startBlock, it.endBlock, it.isShowLoading)
    }!!
    val listTransactionStellar = Transformations.switchMap(fetchParamStellar) {
        transactionStellarRepo.fetchAllTransaction()
    }!!

    fun refreshAll(){
        getAccount()
        if (preferenceHelper.getPlatform() == Constant.ETHEREUM_PLATFORM){
            if (preferenceHelper.getString(Constant.ACCOUNT_ETHEREUM_KEY) == null){
                return
            }
        }else if (preferenceHelper.getPlatform() == Constant.STELLAR_PLATFORM) {
            if (preferenceHelper.getString(Constant.ACCOUNT_STELLAR_KEY) == null){
                return
            }
        }
        getAllTransaction()
        getBalance()
    }
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

    fun getBalance(){
        balanceRepository.fetchBalance()
    }

    fun getAccount(){
        if (preferenceHelper.getPlatform() == Constant.ETHEREUM_PLATFORM){
            accountLiveData.value = preferenceHelper.getString(Constant.ACCOUNT_ETHEREUM_KEY)
        }else if (platform == Constant.STELLAR_PLATFORM){
            accountLiveData.value = preferenceHelper.getString(Constant.ACCOUNT_STELLAR_KEY)
        }
    }

    fun changeAccount(){
        if (platform == Constant.ETHEREUM_PLATFORM) {
            transactionRepository.changeAccount()
        }else if (platform == Constant.STELLAR_PLATFORM){
            transactionStellarRepo.changeAccount()
        }
    }

    fun changeNetwork(){
        if (platform == Constant.ETHEREUM_PLATFORM) {
            transactionRepository.changeNetwork()
        }else if (platform == Constant.STELLAR_PLATFORM){
            transactionStellarRepo.changeNetwork()
        }
    }
}