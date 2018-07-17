package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.BalanceResponse
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.entity.loading
import com.example.dai_pc.android_test.entity.success
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

    fun changeNetwork(id :Int){
        serviceProvider.changeNetwork(id)
    }

}