package com.example.dai_pc.android_test.repository

import android.content.Context
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.NetworkProvider
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository {

    var listNetWorkProvier: List<NetworkProvider> = ArrayList()
    lateinit var networkProviderSelected :NetworkProvider

    @Inject constructor(preferenceHelper: PreferenceHelper,context:Context){
        listNetWorkProvier = Constant.NETWORKS.toList()
        changeNetworkSelect(preferenceHelper.getInt(context.getString(R.string.network_key),1))
    }

    fun changeNetworkSelect(id:Int){
        networkProviderSelected =  listNetWorkProvier!!.firstOrNull{
            it.id == id
        }!!
    }

}