package com.example.dai_pc.android_test.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.database.AppDatabase
import com.example.dai_pc.android_test.entity.Token
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(
        private val networkRepository: NetworkRepository,
        private val walletRepository: WalletRepository,
        private val appDatabase: AppDatabase) {

    fun addToken(token: Token): LiveData<Long> {
        val state = MutableLiveData<Long>()
        token.network = networkRepository.networkProviderSelected.name
        launch(UI) {
            val value = async(CommonPool) {
                appDatabase.tokenDao().addToken(token)
            }
            state.value = value.await()
        }
        return state
    }

    fun getAllToken():LiveData<List<Token>> {
        walletRepository.accountSelected.value?.apply {
            return appDatabase.tokenDao().getAllToken(walletRepository.accountSelected.value!!, networkRepository.networkProviderSelected.name)
        }
        return MutableLiveData<List<Token>>()
    }
}