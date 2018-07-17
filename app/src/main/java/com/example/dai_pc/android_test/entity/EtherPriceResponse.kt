package com.example.dai_pc.android_test.entity

import com.google.gson.annotations.Expose

data class EtherPriceResponse(
        @Expose
        val status: Int,
        @Expose
        val message: String,
        @Expose
        val result: ResponseResult
) {
    data class ResponseResult(
            @Expose
            val ethbtc: Float,
            @Expose
            val ethbtc_timestamp: Long,
            @Expose
            val ethusd: Float,
            @Expose
            val ethusd_timestamp: Long
    )
}
