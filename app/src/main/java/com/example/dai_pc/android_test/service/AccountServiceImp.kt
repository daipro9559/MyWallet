package com.example.dai_pc.android_test.service

import io.reactivex.Single
import org.ethereum.geth.Accounts
import org.ethereum.geth.KeyStore
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Keys
import org.web3j.crypto.Wallet
import org.web3j.crypto.WalletFile
import javax.inject.Inject

class AccountServiceImp @Inject constructor(val keyStore: KeyStore ) :AccountService{


    override fun getAllAccount(): Accounts {
        val acc = keyStore.accounts
        return keyStore.accounts
    }

    // return
    override fun generateAccount(password: String, privateKey:String): Single<WalletFile>{
        return Single.fromCallable {val key =  Keys.createEcKeyPair()
            return@fromCallable Wallet.createStandard(password,key)}

    }
}