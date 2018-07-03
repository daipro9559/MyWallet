package com.example.dai_pc.android_test.entity

import com.google.gson.annotations.Expose

data class TransactionResponse(
        @Expose
        var result: List<Transaction>,
        @Expose
        var status : String,
        @Expose
        var message : String
)