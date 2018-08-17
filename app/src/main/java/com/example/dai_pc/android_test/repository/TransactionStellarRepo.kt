package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.dai_pc.android_test.base.BaseRepository
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.entity.loading
import com.example.dai_pc.android_test.entity.success
import com.example.dai_pc.android_test.service.AccountStellarService
import com.example.stellar.*
import com.example.stellar.responses.TransactionResponse
import com.example.stellar.xdr.AssetType
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import timber.log.Timber
import javax.inject.Inject

class TransactionStellarRepo
@Inject constructor(context: Context,
                    private val walletStellarRepository: WalletStellarRepository,
                    private val accountStellarService: AccountStellarService) : BaseRepository(context) {

    fun sendTransaction(accountIdSource: String, accountIdDestination: String, amount: Float, memo: String) {

        val server = Server("https://horizon-testnet.stellar.org")
        accountStellarService.getSecretSeed(accountIdSource)
                .map {
                    Network.useTestNetwork()
                    val keyPairDestination = KeyPair.fromAccountId(accountIdDestination)
                    val keyPairSource = KeyPair.fromSecretSeed(it)
                    server.accounts().account(keyPairDestination)
                    val sourceAccount = server.accounts().account(keyPairSource)
                    Transaction.Builder(sourceAccount)
                            .addOperation(PaymentOperation.Builder(keyPairDestination,AssetTypeNative(),amount.toString()).build())
                            .addMemo(Memo.text(memo))
                            .build()

                }.map {
                    server.submitTransaction(it)
                }.subscribe ({

                },{
                    // fail send transaction
                })

    }
    fun fetchAllTransaction() : LiveData<Resource<List<TransactionResponse>>> {
        val liveData  = MutableLiveData<Resource<List<TransactionResponse>>>()
        liveData.value = loading()
        launch(UI) {
            val server = Server("https://horizon-testnet.stellar.org")
            val job= async (CommonPool) {
                server.transactions().forAccount(KeyPair.fromAccountId("GBS43BF24ENNS3KPACUZVKK2VYPOZVBQO2CISGZ777RYGOPYC2FT6S3K"))
                        .execute()
            }
            val result = job.await()
            liveData.postValue(success(result.records))
        }
        return liveData
    }
}