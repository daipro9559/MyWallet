package com.example.dai_pc.android_test.view.transaction

import com.example.dai_pc.android_test.entity.TransactionSendObject

interface SendTransactionView {
    fun sendTransaction(transactionSendObject: TransactionSendObject)
    fun errorInput(message:String)
}