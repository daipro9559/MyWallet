package com.example.dai_pc.android_test.view.main.token

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.entity.Token
import com.example.dai_pc.android_test.repository.TokenRepository
import java.math.BigDecimal
import javax.inject.Inject

class TokenViewModel @Inject constructor(private val tokenRepository: TokenRepository,
                                         private val context: Context) : BaseViewModel() {
    private val tokenInforAdded = MutableLiveData<Token>()
    private val tokenBalance = MutableLiveData<Token>()
    val notifyAddCompleted = Transformations.switchMap(tokenInforAdded) {
        tokenRepository.addToken(it)
    }
    val listTokenInfo = MutableLiveData<List<Token>>()
    val valueBalance  = Transformations.switchMap(tokenBalance){
        tokenRepository.getBalance(it)
    }

    fun addToken(contractAddress: String, symbol: String, decimal: Int) {
        tokenInforAdded.value = Token(""// addressWallet
                , "", symbol, decimal, System.currentTimeMillis(), "", "0",contractAddress)
    }

    fun getAllToken() {
        tokenRepository.getAllToken().observeForever {
            listTokenInfo.value = it
        }
    }

    fun getBalance(token: Token) {
        tokenBalance.value = token
    }

}