package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
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
                            appDatabase.walletDao().addWallet(wallet)
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

}