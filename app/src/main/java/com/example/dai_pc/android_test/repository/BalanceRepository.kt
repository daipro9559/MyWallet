package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.BalanceResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.ethereum.geth.Addresses
import java.math.BigInteger
import javax.inject.Inject

class BalanceRepository
@Inject
constructor(private val serviceProvider: ServiceProvider) {
    val balance = MutableLiveData<BigInteger>()

    fun fetchBalance(addresses: String){
        serviceProvider.etherScanApi.fetchBalance("account",
                "balance",
                addresses,
                Constant.API_KEY_ETHEREUM)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    balance.value = it.result
                }, {

                })
    }

}