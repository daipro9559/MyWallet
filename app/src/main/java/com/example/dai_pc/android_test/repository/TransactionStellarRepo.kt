package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.example.dai_pc.android_test.base.BaseRepository
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.entity.error
import com.example.dai_pc.android_test.entity.loading
import com.example.dai_pc.android_test.entity.success
import com.example.dai_pc.android_test.service.AccountStellarService
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import com.example.stellar.*
import com.example.stellar.responses.TransactionResponse
import com.example.stellar.xdr.AssetType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import timber.log.Timber
import javax.inject.Inject

class TransactionStellarRepo
@Inject constructor(context: Context,
                    private val preferenceHelper: PreferenceHelper,
                    private val walletStellarRepository: WalletStellarRepository,
                    private val accountStellarService: AccountStellarService) : BaseRepository(context) {
    private var server: Server

    init {
        server = Server(preferenceHelper.getString(Constant.KEY_NETWORK_STELLAR, Constant.STELLAR_TEST_NET_URL))
    }

    fun sendTransaction(accountIdDestination: String, amount: Float, memo: String): LiveData<Resource<String>> {
        val liveData = MutableLiveData<Resource<String>>()
        liveData.value = loading()
        accountStellarService.getSecretSeed(walletStellarRepository.accountSelected.value!!)
                .map {
                    Network.useTestNetwork()
                    val keyPairDestination = KeyPair.fromAccountId(accountIdDestination)
                    val keyPairSource = KeyPair.fromSecretSeed(it)
                    server.accounts().account(keyPairDestination)
                    val sourceAccount = server.accounts().account(keyPairSource)
                    val transaction = Transaction.Builder(sourceAccount)
                            .addOperation(PaymentOperation.Builder(keyPairDestination, AssetTypeNative(), amount.toString()).build())
                            .addMemo(Memo.text(memo))
                            .build()
                    transaction.sign(keyPairSource)
                    transaction
                }.map {
                    server.submitTransaction(it)
                }.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.e(it.resultXdr)
                    liveData.postValue(success(""))
                }, {
                    Timber.e(it.message)
                    // fail send transaction
                    liveData.postValue(com.example.dai_pc.android_test.entity.error(it.message))
                })
        return liveData
    }

    fun fetchAllTransaction(): LiveData<Resource<ArrayList<TransactionResponse>>> {
        val liveData = MutableLiveData<Resource<ArrayList<TransactionResponse>>>()
        walletStellarRepository.accountSelected.value?.let {
            liveData.value = loading()
            launch(UI) {
                try {
                    val job = async(CommonPool) {
                        server.transactions().forAccount(KeyPair.fromAccountId(walletStellarRepository.accountSelected.value))
                                .execute()
                    }
                    val result = job.await()
                    liveData.postValue(success(result.records))

                } catch (e: Exception) {
                    liveData.postValue(com.example.dai_pc.android_test.entity.error(e.message))
                    Timber.e(e.message)
                }
            }

        }

        return liveData
    }

    fun changeNetwork() {
        server = Server(preferenceHelper.getString(Constant.KEY_NETWORK_STELLAR))
    }

    fun changeAccount() {
        walletStellarRepository.initAccountSelected()
    }
}