package com.example.dai_pc.android_test.view.accounts

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.Transformations
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.repository.WalletRepository
import com.example.dai_pc.android_test.repository.WalletStellarRepository
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import org.ethereum.geth.Account
import javax.inject.Inject

class ManageAccountViewModel
@Inject constructor(private val walletRepository: WalletRepository,
                    private val walletStellarRepository: WalletStellarRepository,
                    preferenceHelper: PreferenceHelper) :BaseViewModel() {
    val fetchAccountEther = MutableLiveData<String>()
    val fetchAccountStellar = MutableLiveData<String>()
    val listAccountEther = walletRepository.accountsLiveData
    val listAccountStellar = Transformations.switchMap(fetchAccountStellar){
        walletStellarRepository.getAllAccount()
    }
    private val platform = preferenceHelper.getPlatform()

    init {
        getAllAccount()
    }

    val deletedAccountNotify = MutableLiveData<Resource<String>>()
    init {
        errorLiveData = walletRepository.error
    }
    fun getAllAccount(){
        if (platform == Constant.STELLAR_PLATFORM) {
            fetchAccountStellar.value = platform
        }else if (platform == Constant.ETHEREUM_PLATFORM) {
            walletRepository.getAllAccount()
        }
    }
    fun selectAccount(address:String){
        walletRepository.saveAccountSelect(address)
    }
    fun deleteAccount(address: String,password:String){
        walletRepository.deleteAccount(address,password).observeForever {
            deletedAccountNotify.value = it
        }
    }

}