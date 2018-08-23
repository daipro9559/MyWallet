package com.example.dai_pc.android_test.base

import com.example.dai_pc.android_test.entity.NetworkProvider

/**
 * Created by dai_pc on 6/15/2018.
 */
object Constant {
    //platform
    val STELLAR_PLATFORM = "stellar platform"
    val ETHEREUM_PLATFORM = "ethereum platform"
    val STELLAR_MAIN_NET_URl = "https://horizon.stellar.org/"
    val STELLAR_TEST_NET_URL = "https://horizon-testnet.stellar.org"

    //Database define
    val APP_DATABASE_NAME = "Wallet_ethereum"
    val DB_VERSION = 1
    val KEY_STORE_FILE_CHILD = "keystore"
    val IS_DEBUG = true

    //network
    val ETHEREUM_NETWORK_NAME = "Ethereum"
    val KOVAN_NETWORK_NAME = "Kovan (Test)"
    val ROPSTEN_NETWORK_NAME = "Ropsten (Test)"
    val STELLAR_MAIN_NAME = "Main net"
    val STELLAR_TEST_NAME= "Test net"
    val RINKEBY_NETWORK_NAME = "Rinkeby (Test)"
    val ETH_SYMBOL = "ETH"


    //key preference
    val KEY_NETWORK_ETHER = "ether_network_preference"
    val KEY_NETWORK_STELLAR= "stellar_network_preference"
    val KEY_REQUIRE_PASSWORD ="require_password_send"
    val KEY_OPEN_APP_FIRST  = "open app first"
    val PLATFORM_KEY = "platform_preference"
    val ACCOUNT_STELLAR_KEY = "account_stellar"
    val ACCOUNT_ETHEREUM_KEY = "account_ethereum"



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
    const val CONTRACT_ADDRESS = "contractaddress"
    const val START_BLOCK = "startblock"
    const val END_BLOCK = "endblock"
    const val SORT = "sort"
    const val API_KEY_ETHEREUM = "QVK4XE2W1H2VM9RXU22FMDYWHJUA5X5FKA"

    //title tab item
    const val TRANSACTIONS="Transactions"
    const val MY_ACCOUNT ="My account"
    const val TEST ="tab test"
    const val RATE ="Rate"
    const val MY_TOKEN = "My Token"

    //key bundle fragment
    const val IS_SEND_TOKEN = "isSendToken"
    const val SYMBOL_TOKEN = "symbol"
    const val BALANCE_TOKEN = "balance_token"
}