package com.example.dai_pc.android_test.view.main

import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.repository.BalanceRepository
import java.math.BigInteger
import javax.inject.Inject

class MainViewModel
@Inject
constructor(private val balanceRepository: BalanceRepository) : BaseViewModel() {
    lateinit var balanceLiveData : MutableLiveData<Resource<BigInteger>>

    fun fetchBalance(){
        balanceLiveData = balanceRepository.fetchBalance()
    }
    fun checkAccountSelected(){

    }
    fun reloadContent(){
        balanceRepository.changeNetwork()
    }
}