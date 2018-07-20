package com.example.dai_pc.android_test.view.wallet

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.repository.WalletRepository
import javax.inject.Inject

class ImportWalletViewModel
@Inject
constructor(val walletRepository: WalletRepository) : BaseViewModel() {
    private val importParamLiveData = MutableLiveData<ImportParam>()
    // address added
    var addressLiveData : LiveData<String>
    init {
        addressLiveData = Transformations.switchMap(importParamLiveData){
            return@switchMap walletRepository.importAccountByKeyStore(it.param,it.exportPassword,it.newPassword)
        }
    }

    fun importByKeyStore(keyStore: String, exportPassword: String,newPassword:String) {
     importParamLiveData.value = ImportParam(keyStore,exportPassword,newPassword)
    }

    fun selectWalletImported(address:String){
        walletRepository.saveAccountSelect(address)
    }

    class ImportParam(val param:String,val exportPassword: String,val newPassword: String)
}