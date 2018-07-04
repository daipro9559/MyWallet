package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.NetworkState
import com.example.dai_pc.android_test.entity.Transaction
import com.example.dai_pc.android_test.entity.TransactionSendedObject
import com.example.dai_pc.android_test.service.AccountService
import com.example.dai_pc.android_test.service.EtherScanApi
import com.example.dai_pc.android_test.ultil.Callback
import com.example.dai_pc.android_test.ultil.ServiceException
import com.google.gson.Gson
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.ethereum.geth.BigInt
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Numeric
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.math.BigInteger
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository
@Inject
constructor(
        private val networkRepository: NetworkRepository,
        private val serviceProvider: ServiceProvider,
        private val accountService: AccountService
) {
    val listTransaction = MutableLiveData<List<Transaction>>()

    fun fetchTransaction(address: String, startBlock: Int, endBlock: Int, callback: (NetworkState) -> Unit) {
        serviceProvider.etherScanApi.fetchTransaction("account", "txlist", address, startBlock, endBlock, "desc", Constant.API_KEY_ETHEREUM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    callback(NetworkState.SUCCESS)
                    listTransaction.value = it.result
                }, {
                    when (it) {
                        is UnknownHostException -> callback(NetworkState.BAD_URL)
                        is SocketTimeoutException -> callback(NetworkState.TIME_OUT)
                        is IOException -> callback(NetworkState.NO_CONENCTION)
                        else -> callback(NetworkState.UNKNOWN)

                    }
                })
    }

    fun sendTransaction(transactionSendedObject: TransactionSendedObject, data: ByteArray?): LiveData<String> {
        val pass = "daipro1995123"
        val liveData = MutableLiveData<String>()
        val value = BigInt(0)
        value.setString(transactionSendedObject.amount.toString(), 10)

        val gasPriceBI = BigInt(0)
        gasPriceBI.setString(transactionSendedObject.gasPrice.toString(), 10)

        val gasLimitBI = BigInt(0)
        gasLimitBI.setString(transactionSendedObject.gasLimit.toString(), 10)


        var web3j = Web3jFactory.build(HttpService(networkRepository.networkProviderSelected.rpcServerUrl))
        val singleData = Single.fromCallable {
            web3j.ethGetTransactionCount(transactionSendedObject.from, DefaultBlockParameterName.LATEST).send()
                    .transactionCount
        }.flatMap { t ->
            accountService.signTransaction(transactionSendedObject.from!!,
                    pass,
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
        return liveData
    }


}


