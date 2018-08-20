package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.dai_pc.android_test.base.BaseRepository
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.*
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Timed
import org.ethereum.geth.Addresses
import timber.log.Timber
import java.math.BigDecimal
import java.math.BigInteger
import javax.inject.Inject

class BalanceRepository
@Inject
constructor(private val walletRepository: WalletRepository,
            private val serviceProvider: ServiceProvider,
            context: Context) : BaseRepository(context){
    val balanceLiveData = MutableLiveData<Resource<String>>()
    fun fetchBalance() {
        balanceLiveData.value = loading()
        walletRepository.accountSelected.value?.let {
            serviceProvider.etherScanApi.fetchBalance("account",
                    "balance",
                    walletRepository.accountSelected.value!!,
                    Constant.API_KEY_ETHEREUM)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        balanceLiveData.value = success(it.result)
                    }, {
                       setError(it)
                    })
        }
        if (walletRepository.accountSelected.value == null){
            balanceLiveData.value = error("")
        }
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