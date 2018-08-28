package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseRepository
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.entity.error
import com.example.dai_pc.android_test.entity.loading
import com.example.dai_pc.android_test.entity.success
import com.example.dai_pc.android_test.service.AccountEthereumService
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.ethereum.geth.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository
@Inject
constructor(
        private val preferenceHelper: PreferenceHelper,
        context: Context,
        private val accountEthereumService: AccountEthereumService) : BaseRepository(context) {
    val accountsLiveData = MutableLiveData<List<Account>>()
    val accountSelected = MutableLiveData<String>()

    fun getAllAccount() {
        val accounts = accountEthereumService.getAllAccount()
        val list = ArrayList<Account>()
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
        accountEthereumService.createAccountWithPassword(pass)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    addressCreated.value = it
                },{})
        return addressCreated
    }

    fun deleteAccount(addresses: String, password: String): LiveData<Resource<String>> {
        val result = MutableLiveData<Resource<String>>()
        result.value = loading()
        try {
            accountEthereumService.deleteAccount(addresses, password)
            result.value = success(context.getString(R.string.delete_account_complete))
        } catch (e: Exception) {
            result.value = error(e.message.toString())
        }
        return result
    }

    fun exportWallet(passwordExport: String): LiveData<String> {
        val exportData = MutableLiveData<String>()
        accountEthereumService.getPassword(context, accountSelected.value.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe({
                    accountEthereumService.exportWallet(accountSelected.value!!, it, passwordExport)
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
        accountSelected.value = preferenceHelper.getString(Constant.ACCOUNT_ETHEREUM_KEY)
    }

    fun saveAccountSelect(addresses: String) {
        preferenceHelper.putString(Constant.ACCOUNT_ETHEREUM_KEY, addresses)
        initAccountSelect()
    }

    fun importAccountByKeyStore(keystore: String, passwordOld: String, passwordNew: String): LiveData<String> {
        val accountLiveData = MutableLiveData<String>()
        accountEthereumService.importByKeyStore(keystore, passwordOld, passwordNew)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    accountEthereumService.savePassword(context, it.address.hex.toString(), passwordNew)
                    accountLiveData.value = it.address.hex.toString()
                }, {
                    setError(it)
                })
        return accountLiveData
    }

    fun importAccountByPrivateKey(privateKey: String, newPassword: String): LiveData<String> {
        val accountLiveData = MutableLiveData<String>()
        accountEthereumService.importByPrivatekey(privateKey, newPassword)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    accountEthereumService.savePassword(context, it.address.hex.toString(), newPassword)
                    accountLiveData.value = it.address.hex.toString()
                }, {
                    setError(it)
                })
        return accountLiveData

    }

    fun updateAccount(addresses: String, oldPassword: String, newPassword: String): LiveData<String> {
        val liveData = MutableLiveData<String>()
        accountEthereumService.updateAccount(addresses, oldPassword, newPassword)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    liveData.value = context.getString(R.string.update_completed)
                }, {
                    setError(it)
                })
        return liveData
    }
}