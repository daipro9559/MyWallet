package com.example.dai_pc.android_test.service

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.ethereum.geth.Account
import org.ethereum.geth.Accounts
import org.web3j.crypto.WalletFile
import java.math.BigInteger

interface AccountEthereumService {
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
    fun savePassword(context: Context,address:String ,password: String)
    fun getPassword(context: Context,address:String ):Single<String>
    fun exportWallet(address: String,password: String,passwordExport: String):Flowable<String>
    fun importByKeyStore(keyStore:String,oldPassword:String,newPassword:String):Single<Account>
    fun importByPrivatekey(privateKey:String, newPassword: String):Single<Account>
    // update password
    fun updateAccount(address:String, oldPassword:String,newPassword:String) : Completable
    fun deleteAccount(address: String,password: String)
}