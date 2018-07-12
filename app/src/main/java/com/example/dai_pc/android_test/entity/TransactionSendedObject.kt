package com.example.dai_pc.android_test.entity

import java.math.BigInteger

class TransactionSendedObject(
        val to: String?,
        val amount: BigInteger?,
        val gasPrice: BigInteger?,
        val gasLimit: BigInteger?,
        val nonce: BigInteger?
) {

    class Builder {
        var to: String? = null
        var amount: BigInteger? = null
        var gasPrice: BigInteger? = null
        var gasLimit: BigInteger? = null
        var nonce: BigInteger? = null
        fun setTo(to: String?) = apply { this.to = to }
        fun setAmount(amount: BigInteger?) = apply { this.amount = amount }
        fun setGasPrice(gasPrice: BigInteger?) = apply { this.gasPrice = gasPrice }
        fun setGasLimit(gasLimit: BigInteger?) = apply { this.gasLimit = gasLimit }
        fun setNonce(nonce: BigInteger?) = apply { this.nonce = nonce }
        fun build() = TransactionSendedObject(to, amount, gasPrice, gasLimit, nonce)
    }

}