package com.example.dai_pc.android_test.view.accounts

import android.arch.lifecycle.*
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
                    val preferenceHelper: PreferenceHelper) :BaseViewModel() {
    val fetchAccountEther = MutableLiveData<String>()
    val fetchAccountStellar = MutableLiveData<String>()
    val listAccountEther = walletRepository.accountsLiveData
    val listAccountStellar = Transformations.switchMap(fetchAccountStellar){
        walletStellarRepository.getAllAccount()
    }
    private val platform = preferenceHelper.getPlatform()
    lateinit var liveDataAccount: LiveData<Account>
    lateinit var liveDataAccountStellar: LiveData<com.example.dai_pc.android_test.entity.Account>
    lateinit var liveDataExport: LiveData<String>
    var liveDataAccountSelect: LiveData<String> = walletRepository.accountSelected

    init {
        getAllAccount()
    }

    val deletedAccountNotify = MutableLiveData<Resource<String>>()
    init {
        errorLiveData = walletRepository.error
    }
    fun getAllAccount(){
        if (preferenceHelper.getPlatform() == Constant.STELLAR_PLATFORM) {
            fetchAccountStellar.value = platform
        }else if (preferenceHelper.getPlatform() == Constant.ETHEREUM_PLATFORM) {
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
    fun createAccount(password: String) {
        liveDataAccount = walletRepository.createAccountFromPassword(password)
    }

    fun export(passwordExport: String) {
        liveDataExport = walletRepository.exportWallet(passwordExport)
    }

    fun selectWallet(walletAddress: String) {
        walletRepository.saveAccountSelect(walletAddress)
    }

    fun createAccountStellar() {
        liveDataAccountStellar =  walletStellarRepository.createAccount()
    }


}