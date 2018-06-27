package com.example.dai_pc.android_test.service

import org.ethereum.geth.Accounts
import org.ethereum.geth.KeyStore
import javax.inject.Inject

class AccountServiceImp @Inject constructor(val keyStore: KeyStore ) :AccountService{


    override fun getAllAccount(): Accounts {
        val acc = keyStore.accounts
        return keyStore.accounts
    }

    override fun createAccount(password: String) {
      var account =   keyStore.newAccount(password)

        var addresses = account.address.hex.toLowerCase()

    }
}