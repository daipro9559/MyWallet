package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.database.AppDatabase
import com.example.dai_pc.android_test.entity.Token
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.NULL_VALUE
import kotlinx.coroutines.experimental.launch
import okhttp3.OkHttpClient
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Bool
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Uint
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Numeric
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(
        private val networkRepository: NetworkRepository,
        private val walletRepository: WalletRepository,
        private val okHttpClient: OkHttpClient,
        private val appDatabase: AppDatabase) {
    private var web3j: Web3j

    init {
        // buildWeb3jClient
        web3j = Web3jFactory.build(HttpService(networkRepository.networkProviderSelected.rpcServerUrl, okHttpClient, false))
    }

    fun addToken(token: Token): LiveData<Long> {
        val state = MutableLiveData<Long>()
        token.address = walletRepository.accountSelected.value!!
        token.network = networkRepository.networkProviderSelected.name
        launch(UI) {
            val value = async(CommonPool) {
                appDatabase.tokenDao().addToken(token)
            }
            state.value = value.await()
        }
        return state
    }

    fun getAllToken(): LiveData<List<Token>> {
        walletRepository.accountSelected.value?.apply {
            return appDatabase.tokenDao().getAllToken(walletRepository.accountSelected.value!!, networkRepository.networkProviderSelected.name)
        }
        return MutableLiveData<List<Token>>()
    }

    fun getBalance( token: Token): LiveData<BigDecimal> {
        val mutableLiveData = MutableLiveData<BigDecimal>()
        launch(UI) {
            val fetchBalance = async (CommonPool){
                val  function = balanceOf(walletRepository.accountSelected.value!!)
                val responseValue = callSmartContractFunction(function,token.address,walletRepository.accountSelected.value!!)
                val response = FunctionReturnDecoder.decode(responseValue,function.outputParameters)
                if(response.size == 0) null else BigDecimal((response[0] as Uint256).value )
            }
            mutableLiveData.value = fetchBalance.await()
        }
        return mutableLiveData


    }

    private fun balanceOf(owner: String) = Function("balanceOf"
            , listOf(Address(owner))
            , listOf(object : TypeReference<Uint256>() {}))
    // address: address is selected
    private fun callSmartContractFunction(function: Function, contractAddress: String, address: String): String {
        val encodeFuntion = FunctionEncoder.encode(function)
        val response = web3j.ethCall(Transaction.createEthCallTransaction(address, contractAddress, encodeFuntion)
                , DefaultBlockParameterName.LATEST)
                .sendAsync()
                .get()
        return response.value

    }

    // send token
    fun createTokenTransferData(to: String, tokenAmount: BigInteger): ByteArray {
        val params = listOf(Address(to), Uint256(tokenAmount))
        val returnTypes = listOf(object : TypeReference<Bool>(){})
        val funtion = Function("transfer",params,returnTypes)
        val encodedFuntion = FunctionEncoder.encode(funtion)
        return Numeric.hexStringToByteArray(encodedFuntion)
    }
}