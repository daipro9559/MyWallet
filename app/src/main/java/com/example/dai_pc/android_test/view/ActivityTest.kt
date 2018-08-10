package com.example.dai_pc.android_test.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.example.dai_pc.android_test.R



class ActivityTest :AppCompatActivity(){

//lateinit var keyPairStellar: KeyPairStellar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        findViewById<Button>(R.id.done).setOnClickListener {
//            keyPairStellar   = KeyPairStellar.random()
//            findViewById<TextView>(R.id.secretSeed).text = keyPairStellar.secretSeed.iterator().toString()
//            findViewById<TextView>(R.id.address).text = keyPairStellar.publicKey
        }
    }
}