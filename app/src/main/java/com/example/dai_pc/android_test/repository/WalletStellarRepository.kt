package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.database.AppDatabase
import com.example.dai_pc.android_test.entity.Account
import com.example.dai_pc.android_test.service.AccountStellarService
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import timber.log.Timber
import java.net.URL
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.Executor
import javax.inject.Inject

class WalletStellarRepository
@Inject constructor(private val accountStellarService: AccountStellarService,
                    private val preferenceHelper: PreferenceHelper,
                    private val appDatabase: AppDatabase) {
    val accountSelected = MutableLiveData<String>()

    init {
        initAccountSelected()
    }

     fun initAccountSelected() {
        accountSelected.value = preferenceHelper.getString(Constant.ACCOUNT_STELLAR_KEY)
    }

    fun createAccount(): LiveData<Account> {
        val liveData = MutableLiveData<Account>()
        accountStellarService.createAccount()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val wallet = Account("", it.accountId, "", Constant.STELLAR_PLATFORM)
                        async(CommonPool) {
                            val friendbotUrl = String.format(
                                    "https://friendbot.stellar.org/?addr=%s",
                                    it.accountId)
                            val response = URL(friendbotUrl).openStream()
                            val body = Scanner(response, "UTF-8").useDelimiter("\\A").next()
                            Timber.e("SUCCESS! You have a new account :)\n$body")
                            appDatabase.walletDao().addWallet(wallet)
                            if (appDatabase.walletDao().getAllWallet(Constant.STELLAR_PLATFORM).value!!.size == 1){
                                preferenceHelper.putString(Constant.ACCOUNT_STELLAR_KEY,it.accountId)
                            }
                    }
                    liveData.value = wallet
                }, {})
        return liveData
    }

    fun importAccountFromSecretSeed(secretSeed: String, name: String, note: String): LiveData<Account> {
        val liveData = MutableLiveData<Account>()

        accountStellarService.importAccountBySecretSeed(secretSeed)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val wallet = Account(name, it.accountId, note, Constant.STELLAR_PLATFORM)
                    launch(UI) {
                        val save = async(CommonPool) {
                            appDatabase.walletDao().addWallet(wallet)
                        }
                        save.await()
                        liveData.value = wallet
                    }
                }, {})
        return liveData
    }

    fun getAllAccount(): LiveData<List<Account>> {
        return appDatabase.walletDao().getAllWallet(Constant.STELLAR_PLATFORM)
    }

    fun saveAccount(idAccount:String){
        preferenceHelper.putString(Constant.ACCOUNT_STELLAR_KEY,idAccount)
        initAccountSelected()
    }

    fun deleteAccount(idAccount: String) : LiveData<Account>{
        val mediatorData = MediatorLiveData<Account>()
        mediatorData.addSource(appDatabase.walletDao().getAccountByAccountId(idAccount)){
            async(CommonPool) {
                appDatabase.walletDao().deleteWallet(it!!)
                async (UI){
                    mediatorData.value = it
                }
            }
        }
        return mediatorData
    }

}