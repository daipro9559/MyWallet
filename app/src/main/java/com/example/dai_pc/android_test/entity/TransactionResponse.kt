package com.example.dai_pc.android_test.entity

import com.google.gson.annotations.Expose

data class TransactionResponse(
        @Expose
        var docs: List<Transaction>,
        @Expose
        var total : Int,
        @Expose
        var limit : Int,
        @Expose
        var page:Int,
        @Expose
        var pages:Int
)