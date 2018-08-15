package com.example.dai_pc.android_test.service

import com.example.stellar.KeyPair
import io.reactivex.Single

interface AccountStellarService {
    fun createAccount(): Single<KeyPair>
    fun singin(priveKey :String) : Single<String>
    fun importAccountBySecretSeed(secretSeed:String) : Single<KeyPair>
    fun exportAccount()
    fun getSecretSeed(accountId :String) : Single<String>
}