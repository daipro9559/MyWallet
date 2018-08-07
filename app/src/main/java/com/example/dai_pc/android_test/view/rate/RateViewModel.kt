package com.example.dai_pc.android_test.view.rate

import android.arch.lifecycle.MutableLiveData
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.entity.EtherPriceResponse
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.repository.BalanceRepository
import javax.inject.Inject

class RateViewModel
@Inject
constructor(val balanceRepository: BalanceRepository) : BaseViewModel() {
    val priceData = MutableLiveData<Resource<EtherPriceResponse>>()

    fun fetchPrice(){
        balanceRepository.fetchPrice().observeForever {
            priceData.value = it
        }
    }
}