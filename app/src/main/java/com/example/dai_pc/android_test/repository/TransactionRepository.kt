package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import com.example.dai_pc.android_test.entity.Transaction
import com.example.dai_pc.android_test.entity.TransactionResponse
import com.example.dai_pc.android_test.entity.TransactionSendedObject
import com.example.dai_pc.android_test.service.EtherScanApi
import com.google.gson.Gson
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.ethereum.geth.BigInt
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository
@Inject
constructor(private val networkRepository: NetworkRepository,
            private val okHttpClient: OkHttpClient
) {
    private lateinit var etherScanApi: EtherScanApi

    init {
        buidApiService(networkRepository.networkProviderSelected.backendUrl)
    }
    fun changeNetwork(id:Int) {
        networkRepository.changeNetworkSelect(id)
        buidApiService(networkRepository.networkProviderSelected.backendUrl)
    }

    fun buidApiService(urlBase:String) {
        etherScanApi = Retrofit.Builder()
                .baseUrl(urlBase)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(EtherScanApi::class.java)
    }

    fun fetchTransaction(address:String):Flowable<List<Transaction>>{
       return etherScanApi.fetchTransactions(address).map { t -> t.docs }
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeOn(Schedulers.io())
    }

    fun sendTransaction(transactionSendedObject: TransactionSendedObject):LiveData<String> {
        val value = BigInt(0)
        value.setString(transactionSendedObject.amount.toString(), 10)

        val gasPriceBI = BigInt(0)
        gasPriceBI.setString(transactionSendedObject.gasPrice.toString(), 10)

        val gasLimitBI = BigInt(0)
        gasLimitBI.setString(transactionSendedObject.gasLimit.toString(), 10)


        var web3j = Web3jFactory.build(HttpService(networkRepository.networkProviderSelected.rpcServerUrl))
        var nonce  = Single.create<BigInteger> {
         web3j.ethGetTransactionCount(transactionSendedObject.from, DefaultBlockParameterName.LATEST).send()
                 .transactionCount
        }
        nonce.flatMap { t -> {  t.apply {  } }
    }
}