package com.example.dai_pc.android_test.view.transaction

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.Toast
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseActivity
import com.example.dai_pc.android_test.databinding.ActivityCreateTransactionBinding
import com.example.dai_pc.android_test.entity.TransactionSendedObject
import com.example.dai_pc.android_test.ultil.BalanceUltil
import java.math.BigInteger
import javax.inject.Inject

class CreateTransactionActivity :BaseActivity<ActivityCreateTransactionBinding>(){



    override fun getLayoutId() = R.layout.activity_create_transaction
    private lateinit var createTransactionViewModel: CreateTransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createTransactionViewModel = ViewModelProviders.of(this,viewModelFactory).get(CreateTransactionViewModel::class.java)
//        var transactionSendedObject = TransactionSendedObject.Builder()
//                .setFrom("0x3b694a36935f96638118b7b17c0afb92850ed985")
//                .setTo("0xB36001A1d3054A1966897Aa5d869D9180ed090A1")
//                .setAmount(BalanceUltil.baseToSubunit("0", 18))// 18 is Ether_decimals
//                .setGasPrice(BigInteger.valueOf(1000000000L))
//                .setGasLimit(BigInteger.valueOf(21000L))
//                .build()
//
//        createTransactionViewModel.createTransaction(transactionSendedObject)?.observe(this, Observer {
//            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
//        })
    }

}