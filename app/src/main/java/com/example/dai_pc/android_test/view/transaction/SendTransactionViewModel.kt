package com.example.dai_pc.android_test.view.transaction

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.entity.TransactionSendObject
import com.example.dai_pc.android_test.repository.BalanceRepository
import com.example.dai_pc.android_test.repository.TokenRepository
import com.example.dai_pc.android_test.repository.TransactionRepository
import com.example.dai_pc.android_test.repository.TransactionStellarRepo
import java.math.BigInteger
import javax.inject.Inject

class SendTransactionViewModel
@Inject
constructor(private val transactionRepository: TransactionRepository,
            private val stellarTransactionRepository: TransactionStellarRepo,
            private val tokenRepository: TokenRepository)
    : BaseViewModel(){

    private val transactionStellarOb = MutableLiveData<SendTransactionStellarOb>()
    val stellarSendTransactionData = Transformations.switchMap(transactionStellarOb){
        stellarTransactionRepository.sendTransaction(it.accountIdDes,it.amount,it.memo)
    }
    private val transactionObjectLiveData = MutableLiveData<TransactionSendObject>()
    val sendResult = Transformations.switchMap(transactionObjectLiveData){
        transactionRepository.sendTransaction(it)
    }

    private val contractAddresLiveData = MutableLiveData<String>()
    val balanceToken = Transformations.switchMap(contractAddresLiveData){
        tokenRepository.getBalanceByContractAddress(it)
    }

    // string of message response
    fun createTransaction(transactionSendObject: TransactionSendObject){
        transactionObjectLiveData.value = transactionSendObject
    }

    fun sendToken(transactionSendObject: TransactionSendObject,contractAddress:String){
        val data = transactionRepository.createTokenTransferData(transactionSendObject.to!!,transactionSendObject.amount!!)
        transactionSendObject.amount = BigInteger.valueOf(0)
        transactionSendObject.to = contractAddress
        transactionSendObject.data = data
        transactionObjectLiveData.value = transactionSendObject
    }

    fun getBalanceToken(contractAddress: String){
        contractAddresLiveData.value = contractAddress
    }
    fun sendTransactionStellar(accountDestination:String,amount:Float,memmo:String){
        transactionStellarOb.value = SendTransactionStellarOb(accountDestination,amount,memmo)
    }

    data class SendTransactionStellarOb(val accountIdDes :String,val amount :Float,val memo :String)


}