package com.example.dai_pc.android_test.service

import android.content.Context
import android.os.Build
import com.example.dai_pc.android_test.ultil.KS
import com.example.dai_pc.android_test.ultil.PasswordManager
import com.example.stellar.KeyPair
import io.reactivex.Single
import javax.inject.Inject

class AccountStellarServiceImp @Inject constructor(val context: Context)
    : AccountStellarService {

    override fun importAccountBySecretSeed(secretSeed: String): Single<KeyPair> {
        return Single.fromCallable{
            val keyPair = KeyPair.fromSecretSeed(secretSeed)
            saveSecretSeed(keyPair)
            keyPair
        }
    }

    override fun exportAccount() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createAccount(): Single<KeyPair> {
        return Single.fromCallable<KeyPair> {
            val keyPair = KeyPair.random()
            saveSecretSeed(keyPair)
            keyPair
        }
    }

    override fun singin(priveKey: String): Single<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun saveSecretSeed(keyPair: KeyPair) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            KS.put(context, keyPair.accountId, String(keyPair.secretSeed))
        } else {
            try {
                PasswordManager.setPassword(keyPair.accountId, String(keyPair.secretSeed), context)
            } catch (e: Exception) {

            }
        }
    }

    override fun getSecretSeed(accountId: String): Single<String> {
        return Single.fromCallable {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String(KS.get(context, accountId)!!)
            } else {
                PasswordManager.getPassword(accountId, context)
            }
        }
    }
}