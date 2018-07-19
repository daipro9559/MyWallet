package com.example.dai_pc.android_test.entity

import java.math.BigInteger

class TransactionSendObject(
        val to: String?,
        val amount: BigInteger?,
        val gasPrice: BigInteger?,
        val gasLimit: BigInteger?,
        val nonce: BigInteger?,
        val pass: String?
) {

    class Builder {
        var to: String? = null
        var amount: BigInteger? = null
        var gasPrice: BigInteger? = null
        var gasLimit: BigInteger? = null
        var nonce: BigInteger? = null
        var pass:String? = null
        fun setTo(to: String?) = apply { this.to = to }
        fun setAmount(amount: BigInteger?) = apply { this.amount = amount }
        fun setGasPrice(gasPrice: BigInteger?) = apply { this.gasPrice = gasPrice }
        fun setGasLimit(gasLimit: BigInteger?) = apply { this.gasLimit = gasLimit }
        fun setNonce(nonce: BigInteger?) = apply { this.nonce = nonce }
        fun setPass(pass: String) = apply { this.pass= pass}
        fun build() = TransactionSendObject(to, amount, gasPrice, gasLimit, nonce,pass)
    }

}