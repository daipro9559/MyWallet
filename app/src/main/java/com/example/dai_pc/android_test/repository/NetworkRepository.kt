package com.example.dai_pc.android_test.repository

import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.NetworkProvider
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository {

    var listNetWorkProvier: List<NetworkProvider> = ArrayList()
    lateinit var networkProviderSelected :NetworkProvider

    @Inject constructor(preferenceHelper: PreferenceHelper){
        listNetWorkProvier = Constant.NETWORKS.toList()
        changeNetworkSelect(preferenceHelper.getInt(Constant.KEY_NETWORK_ID,1))
    }

    fun changeNetworkSelect(id:Int){
        networkProviderSelected =  listNetWorkProvier!!.firstOrNull{
            it.id == id
        }!!
    }

}