package com.example.dai_pc.android_test.view.main

import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.repository.BalanceRepository
import com.example.dai_pc.android_test.repository.NetworkRepository
import com.example.dai_pc.android_test.repository.ServiceProvider
import com.example.dai_pc.android_test.repository.WalletRepository
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import java.math.BigInteger
import javax.inject.Inject

class MainViewModel
@Inject
constructor(private val balanceRepository: BalanceRepository,
            private val walletRepository: WalletRepository,
            private val preferenceHelper: PreferenceHelper
            ) : BaseViewModel() {
    val balanceLiveData  =  MutableLiveData<Resource<BigInteger>>()

    fun fetchBalance(){

    }
    fun changeAddress(){
        walletRepository.initAccountSelect()
    }
    fun changeNetwork(id :Int){
        balanceRepository.changeNetwork(id)
    }

    fun changePlatform(platform: String){
        preferenceHelper.putString(Constant.PLATFORM_KEY,platform)
    }

}