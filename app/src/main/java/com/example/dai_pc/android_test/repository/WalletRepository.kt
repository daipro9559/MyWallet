package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.dai_pc.android_test.AppExecutors
import com.example.dai_pc.android_test.service.AccountService
import org.ethereum.geth.Account
import org.ethereum.geth.EthereumClient
import org.ethereum.geth.Geth
import org.ethereum.geth.KeyStore
import javax.inject.Inject

class WalletRepository
@Inject
constructor(
        val keyStore: KeyStore,
            val context: Context,
            val appExecutors: AppExecutors,
            val accountService: AccountService) {
    val accountsLiveData = MutableLiveData<List<Account>>()
    val addressCreated = MutableLiveData<String>()
    fun getAllAccount() {
        var accounts = accountService.getAllAccount()
        var list = ArrayList<Account>()
        for (i in 0 until accounts!!.size()) {
            list.add(accounts[i])
        }
        accountsLiveData.value = list
    }

    fun createAccountFromPassword(pass: String) {
                    appExecutors.diskIO().execute {
                        val account = keyStore.newAccount(pass)
                        appExecutors.mainThread().execute {
                            addressCreated.value = account.address.hex.toString()
                           var ethereumClient : EthereumClient

                        }
                    }

    }
}