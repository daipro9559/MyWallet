package com.example.dai_pc.android_test.repository

import android.content.Context
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.entity.NetworkProvider
import com.example.dai_pc.android_test.entity.NetworkStellarProvider
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(val preferenceHelper: PreferenceHelper,val context:Context){

    val listNetWorkProvier = listOf(NetworkProvider(1, Constant.ETHEREUM_NETWORK_NAME, Constant.ETH_SYMBOL,
            "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk",
            "https://api.trustwalletapp.com/",
            "https://etherscan.io/", 1, true,"https://api.etherscan.io/"),
            NetworkProvider(2, Constant.KOVAN_NETWORK_NAME, Constant.ETH_SYMBOL,
                    "https://kovan.infura.io/llyrtzQ3YhkdESt2Fzrk",
                    "https://kovan.trustwalletapp.com/",
                    "https://kovan.etherscan.io", 42, false,"https://api-kovan.etherscan.io/"),
            NetworkProvider(3, Constant.RINKEBY_NETWORK_NAME, Constant.ETH_SYMBOL,
                    "https://rinkeby.infura.io/llyrtzQ3YhkdESt2Fzrk",
                    "https://rinkeby.trustwalletapp.com/",
                    "https://rinkeby.etherscan.io", 4, false,"https://api-kovan.etherscan.io/"),
            NetworkProvider(4, Constant.ROPSTEN_NETWORK_NAME, Constant.ETH_SYMBOL,
                    "https://ropsten.infura.io/llyrtzQ3YhkdESt2Fzrk",
                    "https://ropsten.trustwalletapp.com/",
                    "https://ropsten.etherscan.io", 3, false,"https://api-ropsten.etherscan.io/"))
    lateinit var networkProviderSelected : NetworkProvider
    lateinit var networkStellarProvider: NetworkStellarProvider
    init {
        changeNetworkSelect()
    }

    fun changeNetworkSelect(){
        networkProviderSelected =  listNetWorkProvier!!.firstOrNull{
            it.id == preferenceHelper.getInt(Constant.KEY_NETWORK_ETHER,1)
        }!!
    }

}