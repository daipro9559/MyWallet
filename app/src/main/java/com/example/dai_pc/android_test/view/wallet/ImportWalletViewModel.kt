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
    private val importParamPrivateKeyLiveData = MutableLiveData<ImportParamByPrivateKey>()
    private val importParamKeyStoreLiveData = MutableLiveData<ImportParamByKeyStore>()
    // address added
    var addressLiveData : LiveData<String>
    var addressByPrivateKeyLiveData : LiveData<String>
    init {
        addressLiveData = Transformations.switchMap(importParamKeyStoreLiveData){
            return@switchMap walletRepository.importAccountByKeyStore(it.param,it.exportPassword,it.newPassword)
        }
        addressByPrivateKeyLiveData = Transformations.switchMap(importParamPrivateKeyLiveData){
            return@switchMap walletRepository.importAccountByPrivateKey(it.param,it.newPassword)
        }
    }

    fun importByKeyStore(keyStore: String, exportPassword: String,newPassword:String) {
        importParamKeyStoreLiveData.value = ImportParamByKeyStore(keyStore,exportPassword,newPassword)
    }

    fun importByPrivateKey(privateKey:String, newPassword: String){
        importParamPrivateKeyLiveData.value = ImportParamByPrivateKey(privateKey,newPassword)

    }

    fun selectWalletImported(address:String){
        walletRepository.saveAccountSelect(address)
    }

    class ImportParamByPrivateKey(val param:String, val newPassword: String)
    class ImportParamByKeyStore(val param:String, val exportPassword: String, val newPassword: String)
}