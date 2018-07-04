package com.example.dai_pc.android_test.view.network

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.databinding.FragmentListNetworkBinding
import com.example.dai_pc.android_test.entity.NetworkProvider
import com.example.dai_pc.android_test.repository.TransactionRepository
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import javax.inject.Inject

class NetworkFragment : BaseFragment<FragmentListNetworkBinding>() {

    @Inject lateinit var preferenceHelper: PreferenceHelper
    @Inject lateinit var transactionRepository: TransactionRepository
    override fun getlayoutId() = R.layout.fragment_list_network

    companion object {
        fun newInstance(): NetworkFragment {
            var bundle = Bundle()
            var homeFragment = NetworkFragment()
            homeFragment.arguments = bundle
            return homeFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }
    fun initView(){
        viewDataBinding.recycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val adapter = NetworkRecycleViewAdapter(clickCallback = onlick)
        viewDataBinding.recycleView.adapter= adapter
        adapter.swapListItem(Constant.NETWORKS.toList())
    }

    val onlick :(NetworkProvider)->Unit ={
        preferenceHelper.putInt(Constant.KEY_NETWORK_ID,it.id)

    }

}