package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Context
import com.example.dai_pc.android_test.base.BaseRepository
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.*
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import com.example.stellar.KeyPair
import com.example.stellar.Server
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class BalanceRepository
@Inject
constructor(private val walletRepository: WalletRepository,
            private val walletStellarRepository: WalletStellarRepository,
            private val preferenceHelper: PreferenceHelper,
            private val serviceProvider: ServiceProvider,
            context: Context) : BaseRepository(context) {
    private lateinit var server: Server
    val balanceEther = MutableLiveData<Resource<String>>()
    val balanceStellar = MutableLiveData<Resource<String>>()

    init {
        init()
    }

    private fun init() {
        server = Server(preferenceHelper.getString(Constant.KEY_NETWORK_STELLAR, Constant.STELLAR_MAIN_NET_URl))

    }

    fun fetchBalance() {
        if (preferenceHelper.getPlatform() == Constant.ETHEREUM_PLATFORM) {
            balanceEther.value = loading()
            walletRepository.accountSelected.value?.let {
                serviceProvider.etherScanApi.fetchBalance("account",
                        "balance",
                        walletRepository.accountSelected.value!!,
                        Constant.API_KEY_ETHEREUM)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            balanceEther.value = success(it.result)
                        }, {
                            setError(it)
                        })
            }
            if (walletRepository.accountSelected.value == null) {
                balanceEther.value = error("")
            }
        } else if (preferenceHelper.getPlatform() == Constant.STELLAR_PLATFORM) {
            walletStellarRepository.accountSelected.value?.let {
                val account = server.accounts().account(KeyPair.fromAccountId(preferenceHelper.getString(Constant.ACCOUNT_STELLAR_KEY)))
                for ( balance in account.balances){
                    Timber.e(String.format(
                            "Type: %s, Code: %s, Balance: %s",
                            balance.assetType,
                            balance.assetCode,
                            balance.balance))
                }
            }

        }
    }

    fun fetchPrice(): LiveData<Resource<EtherPriceResponse>> {
        val priceLiveData = MutableLiveData<Resource<EtherPriceResponse>>()
        priceLiveData.value = loading()
        serviceProvider.etherScanApi.fetchEtherPrice("stats", "ethprice", Constant.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    priceLiveData.value = success(it)
                }, {
                    priceLiveData.value = error(it.message.toString())
                })

        return priceLiveData
    }

    fun changeNetwork() {
        if (preferenceHelper.getPlatform() == Constant.ETHEREUM_PLATFORM) {
            serviceProvider.changeNetwork()
        } else  if (preferenceHelper.getPlatform() == Constant.STELLAR_PLATFORM){
            init()
        }
    }

}