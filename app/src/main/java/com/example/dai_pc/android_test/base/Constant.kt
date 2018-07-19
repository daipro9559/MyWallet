package com.example.dai_pc.android_test.base

import com.example.dai_pc.android_test.entity.NetworkProvider

/**
 * Created by dai_pc on 6/15/2018.
 */
object Constant {

    //Database define
    val APP_DATABASE_NAME = "Wallet_ethereum"
    val DB_VERSION = 1
    val KEY_STORE_FILE_CHILD = "keystore"
    val IS_DEBUG = true

    //network
    val ETHEREUM_NETWORK_NAME = "Ethereum"
    val KOVAN_NETWORK_NAME = "Kovan (Test)"
    val ROPSTEN_NETWORK_NAME = "Ropsten (Test)"
    val RINKEBY_NETWORK_NAME = "Rinkeby (Test)"
    val ETH_SYMBOL = "ETH"


    //key preference
    val KEY_NETWORK_ID = "network_id_preference"
    val KEY_REQUIRE_PASSWORD ="require_password_send"


    // Network init
    val NETWORKS = arrayOf(NetworkProvider(1, ETHEREUM_NETWORK_NAME, ETH_SYMBOL,
            "https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk",
            "https://api.trustwalletapp.com/",
            "https://etherscan.io/", 1, true,"https://api.etherscan.io/"),
            NetworkProvider(2, KOVAN_NETWORK_NAME, ETH_SYMBOL,
                    "https://kovan.infura.io/llyrtzQ3YhkdESt2Fzrk",
                    "https://kovan.trustwalletapp.com/",
                    "https://kovan.etherscan.io", 42, false,"https://api-kovan.etherscan.io/"),
            NetworkProvider(3, RINKEBY_NETWORK_NAME, ETH_SYMBOL,
                    "https://rinkeby.infura.io/llyrtzQ3YhkdESt2Fzrk",
                    "https://rinkeby.trustwalletapp.com/",
                    "https://rinkeby.etherscan.io", 4, false,"https://api-kovan.etherscan.io/"),
            NetworkProvider(4, ROPSTEN_NETWORK_NAME, ETH_SYMBOL,
                    "https://ropsten.infura.io/llyrtzQ3YhkdESt2Fzrk",
                    "https://ropsten.trustwalletapp.com/",
                    "https://ropsten.etherscan.io", 3, false,"https://api-ropsten.etherscan.io/"))

    interface ErrorCode {
        companion object {

            val UNKNOWN = 1
            val CANT_GET_STORE_PASSWORD = 2
        }
    }

    // query api
    const val MODULE = "module"
    const val ACTION = "action"
    const val API_KEY = "apikey"
    const val ADDRESS = "address"
    const val START_BLOCK = "startblock"
    const val END_BLOCK = "endblock"
    const val SORT = "sort"
    const val API_KEY_ETHEREUM = "QVK4XE2W1H2VM9RXU22FMDYWHJUA5X5FKA"

    //title tab item
    const val TRANSACTIONS="Transactions"
    const val MY_Wallet ="My wallet"
    const val TEST ="tab test"
    const val RATE ="Rate"
    const val MY_TOKEN = "My Token"



}