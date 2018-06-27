package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.service.AccountService
import org.ethereum.geth.Account
import org.ethereum.geth.Accounts
import timber.log.Timber
import java.security.SecureRandom
import javax.inject.Inject

class WalletRepository @Inject constructor(val accountService: AccountService){
    val accountsLiveData: MutableLiveData<List<Account>> =  MutableLiveData()

    fun getAllAccount(){
       var accounts = accountService.getAllAccount()
        var list = ArrayList<Account>()
        for (i in 0 until accounts!!.size()) {
           list.add(accounts[i])
        }
        accountsLiveData.value = list
    }

    fun createAccount(){
        val bytes = ByteArray(256)
        val random = SecureRandom()
        random.nextBytes(bytes)
        var  pass = String(bytes)
        accountService.createAccount(pass)
    }
    fun createAccountFromPassword(pass:String){
        accountService.createAccount(pass)
    }
}