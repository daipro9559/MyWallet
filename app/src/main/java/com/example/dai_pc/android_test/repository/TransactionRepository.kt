package com.example.dai_pc.android_test.repository

import com.example.dai_pc.android_test.entity.Transaction
import com.example.dai_pc.android_test.entity.TransactionResponse
import com.example.dai_pc.android_test.service.EtherScanApi
import com.google.gson.Gson
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository
@Inject
constructor(private val networkRepository: NetworkRepository,
            private val okHttpClient: OkHttpClient
) {
    private lateinit var etherScanApi: EtherScanApi

    init {
        buidApiService(networkRepository.networkProviderSelected.backendUrl)
    }
    fun changeNetwork(id:Int) {
        networkRepository.changeNetworkSelect(id)
        buidApiService(networkRepository.networkProviderSelected.backendUrl)
    }

    fun buidApiService(urlBase:String) {
        etherScanApi = Retrofit.Builder()
                .baseUrl(urlBase)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(EtherScanApi::class.java)
    }

    fun fetchTransaction(address:String):Flowable<List<Transaction>>{
       return etherScanApi.fetchTransactions(address).map { t -> t.docs }
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeOn(Schedulers.io())
    }
}