package com.example.dai_pc.android_test.view.main

import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.repository.BalanceRepository
import java.math.BigInteger
import javax.inject.Inject

class MainViewModel
@Inject
constructor(private val balanceRepository: BalanceRepository) : BaseViewModel() {
    var balanceLiveData = MutableLiveData<BigInteger>()
        get() = balanceRepository.balance

    fun fetchBalance(address:String){
        balanceRepository.fetchBalance(address)
    }
}