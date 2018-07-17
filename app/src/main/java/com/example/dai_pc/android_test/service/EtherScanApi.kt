package com.example.dai_pc.android_test.service

import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.BalanceResponse
import com.example.dai_pc.android_test.entity.EtherPriceResponse
import com.example.dai_pc.android_test.entity.TransactionResponse
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import retrofit2.http.GET
import retrofit2.http.Query

interface EtherScanApi {
    @GET("/transactions?limit=50")
    fun fetchTransactions(@Query("address") adddress: String): Flowable<TransactionResponse>


    @GET("/api")
    fun fetchTransaction(@Query(Constant.MODULE) module: String,
                         @Query(Constant.ACTION) action: String,
                         @Query(Constant.ADDRESS) address: String,
                         @Query(Constant.START_BLOCK) startBlock: Int,
                         @Query(Constant.END_BLOCK) endBlock: Int,
                         @Query(Constant.SORT) sort: String,
                         @Query(Constant.API_KEY) apiKey: String): Flowable<TransactionResponse>

    @GET("/api")
    fun fetchBalance(@Query(Constant.MODULE) module: String,
                     @Query(Constant.ACTION) action: String,
                     @Query(Constant.ADDRESS) address: String,
                     @Query(Constant.API_KEY) apiKey: String): Flowable<BalanceResponse>

    @GET("/api")
    fun fetchEtherPrice(@Query(Constant.MODULE) module: String,
                        @Query(Constant.ACTION) action: String,
                        @Query(Constant.API_KEY) apiKey: String):Flowable<EtherPriceResponse>
}