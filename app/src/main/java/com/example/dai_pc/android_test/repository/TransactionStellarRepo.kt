package com.example.dai_pc.android_test.repository

import android.content.Context
import com.example.dai_pc.android_test.base.BaseRepository
import com.example.dai_pc.android_test.service.AccountStellarService
import com.example.stellar.*
import com.example.stellar.xdr.AssetType
import javax.inject.Inject

class TransactionStellarRepo
@Inject constructor(context: Context,
                    private val walletStellarRepository: WalletStellarRepository,
                    private val accountStellarService: AccountStellarService) : BaseRepository(context) {

    fun sendTransaction(accountIdSource: String, accountIdDestination: String, amount: Float, memo: String) {
        accountStellarService.getSecretSeed(accountIdSource)
                .map {
                    Network.useTestNetwork()
                    val server = Server("https://horizon-testnet.stellar.org")
                    val keyPairDestination = KeyPair.fromAccountId(accountIdDestination)
                    val keyPairSource = KeyPair.fromSecretSeed(it)
                    server.accounts().account(keyPairDestination)
                    val sourceAccount = server.accounts().account(keyPairSource)
                    val transaction = Transaction.Builder(sourceAccount)
                            .addOperation(PaymentOperation.Builder(keyPairDestination,AssetTypeNative(),amount.toString()).build())
                }
    }
}