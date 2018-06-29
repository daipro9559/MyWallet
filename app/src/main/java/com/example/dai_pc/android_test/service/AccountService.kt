package com.example.dai_pc.android_test.service

import android.accounts.Account
import io.reactivex.Single
import org.ethereum.geth.Accounts
import org.web3j.crypto.WalletFile

interface AccountService {
    fun getAllAccount():Accounts
    fun generateAccount(password: String, privateKey:String):Single<WalletFile>
}