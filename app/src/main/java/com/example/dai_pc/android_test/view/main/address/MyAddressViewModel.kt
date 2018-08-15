package com.example.dai_pc.android_test.view.main.address

import android.arch.lifecycle.LiveData
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.repository.WalletRepository
import com.example.dai_pc.android_test.repository.WalletStellarRepository
import org.ethereum.geth.Account
import javax.inject.Inject

class MyAddressViewModel @Inject constructor(private val walletRepository: WalletRepository,
                                             private val walletStellarRepository: WalletStellarRepository) : BaseViewModel() {
    lateinit var liveDataAccount: LiveData<Account>
    lateinit var liveDataAccountStellar: LiveData<com.example.dai_pc.android_test.entity.Account>
    lateinit var liveDataExport: LiveData<String>
    var liveDataAccountSelect: LiveData<String> = walletRepository.accountSelected

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