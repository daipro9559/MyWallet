package com.example.dai_pc.android_test.service

import android.content.Context
import android.os.Build
import com.example.dai_pc.android_test.ultil.KS
import com.example.dai_pc.android_test.ultil.PasswordManager
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.ethereum.geth.*
import org.web3j.crypto.Keys
import org.web3j.crypto.Wallet
import org.web3j.crypto.WalletFile
import java.math.BigInteger
import java.nio.charset.Charset
import javax.inject.Inject

class AccountServiceImp @Inject constructor(val keyStore: KeyStore) : AccountService {



    override fun savePassword(context: Context, address: String, password: String) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                KS.put(context, address, password)
            } else {
                try {
                    PasswordManager.setPassword(address, password, context)
                } catch (e: Exception) {

                }
            }
    }

    override fun getPassword(context: Context, address: String): Single<String> {
        return Single.fromCallable {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String(KS.get(context, address)!!)
            } else {
                PasswordManager.getPassword(address, context)
            }
        }
    }


    override fun signTransaction(addressFrom: String, password: String, addressTo: String, amount: BigInteger, gasPrice: BigInteger, gasLimit: BigInteger, nonce: Long, data: ByteArray?, chainId: Long): Single<ByteArray>? {
        return Single.fromCallable {
            val value = BigInt(0)
            value.setString(amount.toString(), 10)

            val gasPriceBI = BigInt(0)
            gasPriceBI.setString(gasPrice.toString(), 10)

            val gasLimitBI = BigInt(0)
            gasLimitBI.setString(gasLimit.toString(), 10)

            val tx = Transaction(nonce, Address(addressTo), value, gasLimitBI, gasPriceBI, data)

            val chainId = BigInt(chainId)
            val account = findAccount(addressFrom)
            keyStore.unlock(account, password)
            val signed = keyStore.signTx(account, tx, chainId)
            keyStore.lock(account!!.address)
            signed.encodeRLP()
        }
    }


    override fun getAllAccount(): Accounts {
        val acc = keyStore.accounts
        return keyStore.accounts
    }

    // return
    override fun generateAccount(password: String, privateKey: String?): Single<WalletFile> {
        return Single.fromCallable {
            val key = Keys.createEcKeyPair()
            Wallet.createStandard(password, key)
        }

    }

    private fun findAccount(address: String): Account? {
        val accounts = getAllAccount()
        for (i in 0 until accounts.size()) {
            if (accounts.get(i).address.hex.equals(address, ignoreCase = true)) {
                return accounts.get(i)
            }
        }
        return null
    }

    override fun importByKeyStore(keyStoreInput: String, oldPassword: String, newPassword: String): Single<Account> {
        return Single.fromCallable {
            keyStore.importKey(keyStoreInput.toByteArray(Charset.forName("UTF-8")), oldPassword, newPassword)
        }
    }
    override fun exportWallet(address: String,password: String,passwordExport: String): Flowable<String> {
        return Flowable.fromCallable {
            val account = findAccount(address)
            String(keyStore.exportKey(account,password,passwordExport))
        }
    }


}