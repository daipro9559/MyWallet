package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.*
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Timed
import org.ethereum.geth.Addresses
import org.ethereum.geth.Context
import timber.log.Timber
import java.math.BigInteger
import javax.inject.Inject

class BalanceRepository
@Inject
constructor(private val preferenceHelper: PreferenceHelper,
            private val walletRepository: WalletRepository,
            private val serviceProvider: ServiceProvider) {

    fun fetchBalance() :MutableLiveData<Resource<BigInteger>> {
        val balance = MutableLiveData<Resource<BigInteger>>()
        balance.value = loading()
        walletRepository.accountSelected.value?.let {
            serviceProvider.etherScanApi.fetchBalance("account",
                    "balance",
                    walletRepository.accountSelected.value!!,
                    Constant.API_KEY_ETHEREUM)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        balance.value = success(it.result)
                    }, {
                        Timber.e(it.message)
                    })
        }

       return balance
    }

    fun fetchPrice() : LiveData<Resource<EtherPriceResponse>>{
        val  priceLiveData = MutableLiveData<Resource<EtherPriceResponse>>()
        priceLiveData.value = loading()
        serviceProvider.etherScanApi.fetchEtherPrice("stats","ethprice",Constant.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    priceLiveData.value = success(it)
                },{
                    priceLiveData.value = error(it.message.toString())
                })

        return priceLiveData
    }

    fun changeNetwork(id :Int){
        serviceProvider.changeNetwork(id)
    }

}