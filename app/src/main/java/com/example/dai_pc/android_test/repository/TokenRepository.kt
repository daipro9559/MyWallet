package com.example.dai_pc.android_test.repository

import com.example.dai_pc.android_test.database.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(
        val walletRepository: WalletRepository,
        val appDatabase: AppDatabase) {
    fun addToken() {

    }

    fun getAllToken(){

    }

}