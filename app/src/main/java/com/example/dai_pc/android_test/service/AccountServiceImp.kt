package com.example.dai_pc.android_test.service

import io.reactivex.Single
import org.ethereum.geth.*
import org.web3j.crypto.Keys
import org.web3j.crypto.Wallet
import org.web3j.crypto.WalletFile
import java.math.BigInteger
import javax.inject.Inject

class AccountServiceImp @Inject constructor(val keyStore: KeyStore ) :AccountService{
    override fun signTransaction(addressFrom: String, password: String, addressTo: String, amount: BigInteger, gasPrice: BigInteger, gasLimit: BigInteger, nonce: Long, data: ByteArray?, chainId: Long): Single<ByteArray>? {
        return Single.fromCallable {
            val value = BigInt(0)
            value.setString(amount.toString(), 10)

            val gasPriceBI = BigInt(0)
            gasPriceBI.setString(gasPrice.toString(), 10)

            val gasLimitBI = BigInt(0)
            gasLimitBI.setString(gasLimit.toString(), 10)

            val tx = Transaction(nonce, Address(addressTo),value,gasLimitBI,gasPriceBI,data)

            val chainId = BigInt(chainId)
            val account = findAccount(addressFrom)
            keyStore.unlock(account,password)
            val signed  = keyStore.signTx(account,tx,chainId)
            keyStore.lock(account!!.address)
            signed.encodeRLP() }


    }

    override fun getAllAccount(): Accounts {
        val acc = keyStore.accounts
        return keyStore.accounts
    }

    // return
    override fun generateAccount(password: String, privateKey:String?): Single<WalletFile>{
        return Single.fromCallable {val key =  Keys.createEcKeyPair()
            return@fromCallable Wallet.createStandard(password,key)}

    }

    private fun findAccount(address:String):Account?{
        val accounts  = getAllAccount()
        for (i in 0 until accounts.size()){
            if (accounts.get(i).address.hex.equals(address, ignoreCase = true)) {
                return accounts.get(i)
            }
        }
        return null
    }
}