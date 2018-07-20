package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.dai_pc.android_test.AppExecutors
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.di.AppScope
import com.example.dai_pc.android_test.service.AccountService
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.ethereum.geth.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository
@Inject
constructor(
        private val preferenceHelper: PreferenceHelper,
        private val keyStore: KeyStore,
        private val context: Context,
        private val appExecutors: AppExecutors,
        private val accountService: AccountService) {
    val accountsLiveData = MutableLiveData<List<Account>>()
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
        initAccountSelect()
    }

    fun createAccountFromPassword(pass: String): LiveData<Account> {
        val addressCreated = MutableLiveData<Account>()
        appExecutors.diskIO().execute {
            val account = keyStore.newAccount(pass)
            accountService.savePassword(context,account.address.hex.toString(),pass)
            appExecutors.mainThread().execute {
                addressCreated.value = account
            }
        }
        return addressCreated

    }

    fun deleteAccount(addresses: String) {

    }

    fun exportWallet(passwordExport: String): LiveData<String> {
        val exportData = MutableLiveData<String>()
        accountService.getPassword(context,accountSelected.value.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe( {
                       accountService.exportWallet(accountSelected.value!!, it, passwordExport)
                               .subscribeOn(Schedulers.computation())
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe{
                                   exportData.value = it
                               }

                },{

                })

        return exportData

    }

    fun initAccountSelect() {
        accountSelected.value = preferenceHelper.getString(context.getString(R.string.wallet_key))
    }

    fun saveAccountSelect(addresses: String) {
        preferenceHelper.putString(context.getString(R.string.wallet_key), addresses)
        initAccountSelect()
    }

    fun importAccountByKeyStore(keystore: String, passwordOld: String, passwordNew: String): LiveData<String> {
        val accountLiveData = MutableLiveData<String>()
        accountService.importByKeyStore(keystore, passwordOld, passwordNew)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                   accountService.savePassword(context,it.address.hex.toString(),passwordNew)
                    accountLiveData.value = it.address.hex.toString()
                }, {

                })
        return accountLiveData
    }
}