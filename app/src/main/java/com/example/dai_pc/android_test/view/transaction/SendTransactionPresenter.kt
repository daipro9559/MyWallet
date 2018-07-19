package com.example.dai_pc.android_test.view.transaction

import com.example.dai_pc.android_test.entity.TransactionSendObject

interface SendTransactionPresenter<V> {
    fun validateData(transactionSendObject: TransactionSendObject,isRequireCheckPass:Boolean)
    fun bindView(v:V)
    fun dropView()
}