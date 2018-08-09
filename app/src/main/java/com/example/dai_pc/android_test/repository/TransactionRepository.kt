package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseRepository
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.*
import com.example.dai_pc.android_test.service.AccountService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.ethereum.geth.BigInt
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Numeric
import java.io.IOException
import java.math.BigInteger
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository
@Inject
constructor(
        context: Context,
        private val networkRepository: NetworkRepository,
        private val serviceProvider: ServiceProvider,
        private val accountService: AccountService,
        private val walletRepository: WalletRepository
) : BaseRepository(context) {

    fun fetchTransaction(startBlock: Int, endBlock: Int, isShowLoading: Boolean): LiveData<Resource<List<Transaction>>> {
        val listTransaction = MutableLiveData<Resource<List<Transaction>>>()
        listTransaction.value = loading()
        walletRepository.accountSelected.value?.let {
            serviceProvider.etherScanApi.fetchTransaction("account", "txlist", walletRepository.accountSelected.value!!, startBlock, endBlock, "desc", Constant.API_KEY_ETHEREUM)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        listTransaction.value = success(it.result)
                    }) {
                        listTransaction.value = error(it.message!!)
                        setError(it)
                    }
        }
        if (walletRepository.accountSelected.value == null) {
            listTransaction.value = error(context.getString(R.string.no_account))
        }

        return listTransaction
    }

    fun sendTransaction(transactionSendObject: TransactionSendObject): LiveData<Resource<String>> {
        val liveData = MutableLiveData<Resource<String>>()
        liveData.value = loading()
        accountService.getPassword(context, walletRepository.accountSelected.value!!)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val value = BigInt(0)
                    value.setString(transactionSendObject.amount.toString(), 10)
                    val gasPriceBI = BigInt(0)
                    gasPriceBI.setString(transactionSendObject.gasPrice.toString(), 10)
                    val gasLimitBI = BigInt(0)
                    gasLimitBI.setString(transactionSendObject.gasLimit.toString(), 10)
                    var web3j = Web3jFactory.build(HttpService(networkRepository.networkProviderSelected.rpcServerUrl))
                    val singleData = Single.fromCallable {
                        web3j.ethGetTransactionCount(walletRepository.accountSelected.value, DefaultBlockParameterName.LATEST)
                                .send()
                                .transactionCount
                    }.flatMap { t ->
                        accountService.signTransaction(walletRepository.accountSelected.value!!,
                                it,
                                transactionSendObject.to!!,
                                transactionSendObject.amount!!,
                                transactionSendObject.gasPrice!!,
                                transactionSendObject.gasLimit!!,
                                t.toLong(),
                                transactionSendObject.data,
                                networkRepository.networkProviderSelected.ChanId.toLong())
                    }.flatMap { singleMessage ->
                        Single.fromCallable {
                            val hextString = Numeric.toHexString(singleMessage)
                            val raw = web3j.ethSendRawTransaction(hextString).send()
                            if (raw.hasError()) {
                                throw Exception(raw.error.message)
                            } else {
                                raw.transactionHash
                            }
                        }
                    }
                    singleData.observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe({
                                liveData.value = success(it.toString())
                            }, {
                                liveData.value = error(it.message.toString())
                                setError(it)
                            })
                }, {})
        return liveData

    }

    // create data for send token
    fun createTokenTransferData(to: String, tokenAmount: BigInteger): ByteArray {
        val params = listOf(Address(to), Uint256(tokenAmount))
        val returnTypes = listOf(object : TypeReference<Bool>() {})
        val funtion = Function("transfer", params, returnTypes)
        val encodedFuntion = FunctionEncoder.encode(funtion)
        return Numeric.hexStringToByteArray(encodedFuntion)
    }
}


