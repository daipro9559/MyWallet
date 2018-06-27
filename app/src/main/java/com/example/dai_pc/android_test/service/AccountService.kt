package com.example.dai_pc.android_test.service

import android.accounts.Account
import org.ethereum.geth.Accounts

interface AccountService {
    fun getAllAccount():Accounts
    fun createAccount(password: String)
}