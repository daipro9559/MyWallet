package com.example.dai_pc.android_test.service

import com.example.dai_pc.android_test.entity.TransactionResponse
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import retrofit2.http.GET
import retrofit2.http.Query

interface EtherScanApi{
    @GET("/transactions?limit=50")
fun fetchTransactions(@Query("address") adddress:String):Flowable<TransactionResponse>
}