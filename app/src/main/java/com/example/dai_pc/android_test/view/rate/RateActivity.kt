package com.example.dai_pc.android_test.view.rate

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseActivity
import com.example.dai_pc.android_test.databinding.ActivityRateBinding
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.view.main.MainViewModel
import java.math.BigDecimal

class RateActivity :BaseActivity<ActivityRateBinding>(){

    override fun getLayoutId() = R.layout.activity_rate

    private lateinit var rateViewModel: RateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.my_balance_account)
        enableHomeHomeAsUp()
        rateViewModel = ViewModelProviders.of(this,viewModelFactory)[RateViewModel::class.java]
        rateViewModel.priceData.observe(this, Observer {
            if (it!!.status == Resource.Status.LOADING){
                viewDataBinding.txtPriceUSD.text="..."
                viewDataBinding.txtPriceBTC.text="..."
                viewDataBinding.txtPriceUSD.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.title_fade))
                viewDataBinding.txtPriceBTC.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.title_fade))
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
//        mainViewModel.balanceLiveData.observe(this, Observer {
//            if (it!!.status == Resource.Status.LOADING){
//                viewDataBinding.txtBalance.text="..."
//                viewDataBinding.txtPriceBTC.startAnimation(AnimationUtils.loadAnimation(applicationContext,R.anim.title_fade))
//            }else{
//                viewDataBinding.txtBalance.clearAnimation()
//                if (viewDataBinding.swipeRefresh.isRefreshing){
//                    viewDataBinding.swipeRefresh.isRefreshing = false
//                }
//            }
//            it?.t?.let {
//                val data = BigDecimal(it, 18)
//                viewDataBinding.txtBalance.text = data.toFloat().toString() + " ETH"
//            }
//        })
    }

    fun refresh(){

    }
}