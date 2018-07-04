package com.example.dai_pc.android_test.service

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Single
import org.ethereum.geth.Accounts
import org.web3j.crypto.WalletFile
import java.math.BigInteger

interface AccountService {
    fun getAllAccount():Accounts
    fun generateAccount(password: String, privateKey:String?):Single<WalletFile>
    fun signTransaction(addressFrom:String,
                        password: String,
                        addressTo:String,
                        amount:BigInteger,
                        gasPrice:BigInteger,
                        gasLimit:BigInteger,
                        nonce:Long,
                        data: ByteArray?,
                        chainId:Long):Single<ByteArray>?
    fun savePassword(context: Context,address:String ,password: String):Completable
    fun getPassword(context: Context,address:String ):Single<String>
}