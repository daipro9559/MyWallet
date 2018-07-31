package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.dai_pc.android_test.AppExecutors
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseRepository
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.entity.error
import com.example.dai_pc.android_test.entity.loading
import com.example.dai_pc.android_test.entity.success
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
        private val accountService: AccountService) : BaseRepository() {
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
            accountService.savePassword(context, account.address.hex.toString(), pass)
            appExecutors.mainThread().execute {
                addressCreated.value = account
            }
        }
        return addressCreated

    }

    fun deleteAccount(addresses: String, password: String): LiveData<Resource<String>> {
        val result = MutableLiveData<Resource<String>>()
        result.value = loading()
        try {
            accountService.deleteAccount(addresses, password)
            result.value = success(context.getString(R.string.delete_account_complete))
        } catch (e: Exception) {
            result.value = error(e.message.toString())

        }
        return result
    }

    fun exportWallet(passwordExport: String): LiveData<String> {
        val exportData = MutableLiveData<String>()
        accountService.getPassword(context, accountSelected.value.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe({
                    accountService.exportWallet(accountSelected.value!!, it, passwordExport)
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                exportData.value = it
                            }

                }, {

                })

        return exportData

    }

    fun initAccountSelect() {
        accountSelected.value = preferenceHelper.getString(context.getString(R.string.account_key))
    }

    fun saveAccountSelect(addresses: String) {
        preferenceHelper.putString(context.getString(R.string.account_key), addresses)
        initAccountSelect()
    }

    fun importAccountByKeyStore(keystore: String, passwordOld: String, passwordNew: String): LiveData<String> {
        val accountLiveData = MutableLiveData<String>()
        accountService.importByKeyStore(keystore, passwordOld, passwordNew)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    accountService.savePassword(context, it.address.hex.toString(), passwordNew)
                    accountLiveData.value = it.address.hex.toString()
                }, {

                })
        return accountLiveData
    }

    fun importAccountByPrivateKey(privateKey: String, newPassword: String): LiveData<String> {
        val accountLiveData = MutableLiveData<String>()
        accountService.importByPrivatekey(privateKey, newPassword)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    accountService.savePassword(context, it.address.hex.toString(), newPassword)
                    accountLiveData.value = it.address.hex.toString()
                }, {})
        return accountLiveData

    }

    fun updateAccount(addresses: String, oldPassword: String, newPassword: String): LiveData<String> {
        val liveData = MutableLiveData<String>()
        accountService.updateAccount(addresses, oldPassword, newPassword)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    liveData.value = context.getString(R.string.update_completed)
                }, {
                    liveData.value = it.message
                })
        return liveData
    }
}