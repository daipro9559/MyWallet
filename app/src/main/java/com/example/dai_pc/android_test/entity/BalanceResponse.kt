package com.example.dai_pc.android_test.entity

import com.google.gson.annotations.Expose
import java.math.BigInteger

data class BalanceResponse(
        @Expose
        val status : Int,
        @Expose
        val message: String,
        @Expose
        val result : BigInteger

)