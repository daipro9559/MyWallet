package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.dai_pc.android_test.AppExecutors
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.service.AccountService
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import org.ethereum.geth.Account
import org.ethereum.geth.EthereumClient
import org.ethereum.geth.Geth
import org.ethereum.geth.KeyStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository
@Inject
constructor(
        val preferenceHelper: PreferenceHelper,
        val keyStore: KeyStore,
        val context: Context,
        val appExecutors: AppExecutors,
        val accountService: AccountService) {
    val accountsLiveData = MutableLiveData<List<Account>>()
    val addressCreated = MutableLiveData<String>()
    val accountSelected = MutableLiveData<String>()
    fun getAllAccount() {
        var accounts = accountService.getAllAccount()
        var list = ArrayList<Account>()
        for (i in 0 until accounts!!.size()) {
            list.add(accounts[i])
        }
        accountsLiveData.value = list
    }

    init {
        getAccountSetting()
    }

    fun createAccountFromPassword(pass: String) {
        appExecutors.diskIO().execute {
            val account = keyStore.newAccount(pass)
            appExecutors.mainThread().execute {
                addressCreated.value = account.address.hex.toString()
                var ethereumClient: EthereumClient

            }
        }

    }

    fun getAccountSetting() {
        accountSelected.value = preferenceHelper.getString(context.getString(R.string.wallet_key))
    }
}