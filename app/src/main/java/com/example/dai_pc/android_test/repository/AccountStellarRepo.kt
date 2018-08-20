package com.example.dai_pc.android_test.repository

import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import com.example.stellar.Server
import javax.inject.Singleton

@Singleton
class AccountStellarRepo(private val preferenceHelper: PreferenceHelper) {
    private lateinit var server : Server

    init {
        buildServer()
    }

    private fun buildServer(){
    }

    fun fetchBalance(){

    }
}