package com.example.dai_pc.android_test.entity


data class NetworkProvider(var id: Int,
                           var name: String,
                           var symbol: String,
                           var rpcServerUrl: String,
                           var backendUrl: String,
                           var etherscanUrl: String,
                           var ChanId: Int,
                           var isMainNetwork: Boolean,
                           var baseUrl: String) {

}