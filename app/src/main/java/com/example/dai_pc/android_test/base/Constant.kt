package com.example.dai_pc.android_test.base

import com.example.dai_pc.android_test.entity.NetworkProvider

/**
 * Created by dai_pc on 6/15/2018.
 */
object Constant {

    //Database define
    val APP_DATABASE_NAME = "Wallet_ethereum"
    val DB_VERSION = 1
    val KEY_STORE_FILE_CHILD = "keystore/keystore"
    val IS_DEBUG = true

    //network
    val ETHEREUM_NETWORK_NAME = "Ethereum"
    val CLASSIC_NETWORK_NAME = "Ethereum Classic"
    val POA_NETWORK_NAME = "POA Network"
    val KOVAN_NETWORK_NAME = "Kovan (Test)"
    val ROPSTEN_NETWORK_NAME = "Ropsten (Test)"
    val ETH_SYMBOL = "ETH"
    val POA_SYMBOL = "POA"
    val ETC_SYMBOL = "ETC"


    //key preference
    val KEY_NETWORK_ID = "network_id_preference"


    // Network init
    val NETWORKS = arrayOf<NetworkProvider>(NetworkProvider(1, ETHEREUM_NETWORK_NAME, ETH_SYMBOL,
            "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk",
            "https://api.trustwalletapp.com/",
            "https://etherscan.io/", 1, true),
            NetworkProvider(2, CLASSIC_NETWORK_NAME, ETC_SYMBOL,
                    "https://mewapi.epool.io/",
                    "https://classic.trustwalletapp.com",
                    "https://gastracker.io", 61, true),
            NetworkProvider(3, POA_NETWORK_NAME, POA_SYMBOL,
                    "https://core.poa.network",
                    "https://poa.trustwalletapp.com", "poa", 99, false),
            NetworkProvider(4, KOVAN_NETWORK_NAME, ETH_SYMBOL,
                    "https://kovan.infura.io/llyrtzQ3YhkdESt2Fzrk",
                    "https://kovan.trustwalletapp.com/",
                    "https://kovan.etherscan.io", 42, false),
            NetworkProvider(5, ROPSTEN_NETWORK_NAME, ETH_SYMBOL,
                    "https://ropsten.infura.io/llyrtzQ3YhkdESt2Fzrk",
                    "https://ropsten.trustwalletapp.com/",
                    "https://ropsten.etherscan.io", 3, false))
}