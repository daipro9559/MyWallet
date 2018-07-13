package com.example.dai_pc.android_test.view.main.address

import android.arch.lifecycle.LiveData
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.repository.WalletRepository
import org.ethereum.geth.Account
import javax.inject.Inject

class MyAddressViewModel @Inject constructor(private val walletRepository: WalletRepository) : BaseViewModel(){
    lateinit var liveDataAccount: LiveData<Account>
    lateinit var liveDataExport: LiveData<String>

    fun createAccount(password:String){
        liveDataAccount = walletRepository.createAccountFromPassword(password)
    }

    fun export(passwordExport: String){
        liveDataExport = walletRepository.exportWallet(passwordExport)
    }
}