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
     val notifyAddCompleted  = Transformations.switchMap(tokenInforAdded){
         tokenRepository.addToken(it)
    }
     val listTokenInfo = MutableLiveData<List<Token>>()

    fun addToken(address:String,symbol:String,decimal: Int){
        tokenInforAdded.value = Token(address,"",symbol,decimal,System.currentTimeMillis(),"","0")
    }

    fun getAllToken(){
        tokenRepository.getAllToken().observeForever {
            listTokenInfo.value = it
        }
    }

    fun getBalance(address: String, token: Token){

    }
}