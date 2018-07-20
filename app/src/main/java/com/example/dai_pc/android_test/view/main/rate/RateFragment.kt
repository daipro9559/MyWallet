package com.example.dai_pc.android_test.view.main.rate

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentRateBinding
import com.example.dai_pc.android_test.entity.Resource

class RateFragment :BaseFragment<FragmentRateBinding>(){
    private lateinit var rateViewModel: RateViewModel

    companion object {
        fun newsInstance() : RateFragment{
            val rateFragment = RateFragment()
           return rateFragment
        }
    }

    override fun getlayoutId() = R.layout.fragment_rate

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rateViewModel = ViewModelProviders.of(this,viewModelFactory)[RateViewModel::class.java]
        rateViewModel.priceData.observe(this, Observer {
            if (it!!.status == Resource.Status.LOADING){
                viewDataBinding.txtPriceUSD.text="..."
                viewDataBinding.txtPriceBTC.text="..."
                viewDataBinding.txtPriceUSD.startAnimation(AnimationUtils.loadAnimation(context,R.anim.title_fade))
                viewDataBinding.txtPriceBTC.startAnimation(AnimationUtils.loadAnimation(context,R.anim.title_fade))
            }else{
                viewDataBinding.txtPriceUSD.clearAnimation()
                viewDataBinding.txtPriceBTC.clearAnimation()
                if (viewDataBinding.swipeRefresh.isRefreshing){
                    viewDataBinding.swipeRefresh.isRefreshing = false
                }
            }
            it?.t?.let {
                viewDataBinding.txtPriceUSD.text = it.result.ethusd.toString()+ " USD"
                viewDataBinding.txtPriceBTC.text = it.result.ethbtc.toString() +" BTC"
            }
        })
        refresh()
        viewDataBinding.swipeRefresh.setOnRefreshListener {
            refresh()
        }
    }

    fun refresh(){
        rateViewModel.fetchPrice()
    }
}