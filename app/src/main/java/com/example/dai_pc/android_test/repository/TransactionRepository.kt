package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.*
import com.example.dai_pc.android_test.service.AccountService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.ethereum.geth.BigInt
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Numeric
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository
@Inject
constructor(
        private val context: Context,
        private val networkRepository: NetworkRepository,
        private val serviceProvider: ServiceProvider,
        private val accountService: AccountService,
        private val walletRepository: WalletRepository
) {


    fun fetchTransaction(startBlock: Int, endBlock: Int, callback: (NetworkState) -> Unit) :LiveData<Resource<List<Transaction>>>{
        val listTransaction = MutableLiveData<Resource<List<Transaction>>>()
        listTransaction.value =  loading()
        walletRepository.accountSelected.value?.let {
            serviceProvider.etherScanApi.fetchTransaction("account", "txlist", walletRepository.accountSelected.value!!, startBlock, endBlock, "desc", Constant.API_KEY_ETHEREUM)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        callback(NetworkState.SUCCESS)
                        listTransaction.value = success(it.result)
                    }) {
                        listTransaction.value = error(it.message!!)
                        when (it) {
                            is UnknownHostException -> callback(NetworkState.BAD_URL)
                            is SocketTimeoutException -> callback(NetworkState.TIME_OUT)
                            is IOException -> callback(NetworkState.NO_CONENCTION)
                            else -> callback(NetworkState.UNKNOWN)

                        }
                    }
        }
        if (walletRepository.accountSelected.value ==null){
            listTransaction.value = error("No Account")
        }

        return listTransaction
    }

    fun sendTransaction(transactionSendedObject: TransactionSendedObject, data: ByteArray?): LiveData<String> {
        val liveData = MutableLiveData<String>()
        accountService.getPassword(context,walletRepository.accountSelected.value!!)
                 .subscribeOn(Schedulers.computation())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe({
                     val value = BigInt(0)
                     value.setString(transactionSendedObject.amount.toString(), 10)
                     val gasPriceBI = BigInt(0)
                     gasPriceBI.setString(transactionSendedObject.gasPrice.toString(), 10)
                     val gasLimitBI = BigInt(0)
                     gasLimitBI.setString(transactionSendedObject.gasLimit.toString(), 10)
                     var web3j = Web3jFactory.build(HttpService(networkRepository.networkProviderSelected.rpcServerUrl))
                     val singleData = Single.fromCallable {
                         web3j.ethGetTransactionCount(walletRepository.accountSelected.value, DefaultBlockParameterName.LATEST).send()
                                 .transactionCount
                     }.flatMap { t ->
                         accountService.signTransaction(walletRepository.accountSelected.value!!,
                                 it,
                                 transactionSendedObject.to!!,
                                 transactionSendedObject.amount!!,
                                 transactionSendedObject.gasPrice!!,
                                 transactionSendedObject.gasLimit!!,
                                 t.toLong(),
                                 data,
                                 networkRepository.networkProviderSelected.ChanId.toLong())
                     }.flatMap { singleMessage ->
                         Single.fromCallable {
                             val hextString = Numeric.toHexString(singleMessage)
                             val raw = web3j.ethSendRawTransaction(hextString).send()
                             if (raw.hasError()) {
                                 raw.error.message
                             } else {
                                 raw.transactionHash
                             }
                         }
                     }
                     singleData.observeOn(AndroidSchedulers.mainThread())
                             .subscribeOn(Schedulers.io())
                             .subscribe({
                                 liveData.value = it
                             }, {

                             })
                 },{})
        return liveData

    }


}


