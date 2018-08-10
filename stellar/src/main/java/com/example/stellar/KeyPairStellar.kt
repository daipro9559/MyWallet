package com.example.stellar

import org.stellar.sdk.KeyPair

class KeyPairStellar {

    companion object {
        fun random() : KeyPairStellar {
            val keyPair = KeyPair.random()
            val  keyPairStellar = KeyPairStellar()
            keyPairStellar.publicKey = keyPair.publicKey.toString()
            keyPairStellar.secretSeed  = keyPair.secretSeed.iterator().toString()
            return keyPairStellar
        }
    }

    lateinit var publicKey : String
    lateinit var secretSeed : String


}