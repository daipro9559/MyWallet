package com.example.dai_pc.android_test.repository

import com.example.dai_pc.android_test.service.EtherScanApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * change service api when change user change network
 */
@Singleton
class ServiceProvider
@Inject
constructor(val okHttpClient: OkHttpClient,
            private val networkRepository: NetworkRepository)
{
    init {
        buidApiService(networkRepository.networkProviderSelected.baseUrl)
    }
    lateinit var etherScanApi: EtherScanApi
    fun buidApiService(urlBase: String) {
        etherScanApi = Retrofit.Builder()
                .baseUrl(urlBase)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(EtherScanApi::class.java)
    }
    fun changeNetwork(id: Int) {
        networkRepository.changeNetworkSelect(id)
        buidApiService(networkRepository.networkProviderSelected.baseUrl)
    }
}