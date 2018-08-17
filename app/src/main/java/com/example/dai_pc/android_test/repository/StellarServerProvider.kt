package com.example.dai_pc.android_test.repository

import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import com.example.stellar.Server
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StellarServerProvider @Inject constructor(val preferenceHelper: PreferenceHelper){
    lateinit var server : Server
    init {

    }

    private fun changeServer(){
    }
}