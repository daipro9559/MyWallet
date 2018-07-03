package com.example.dai_pc.android_test.view.transactions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.dai_pc.android_test.AppExecutors
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentHomeBinding
import com.example.dai_pc.android_test.repository.TransactionRepository
import com.example.dai_pc.android_test.ultil.BalanceUltil
import okhttp3.OkHttpClient
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import timber.log.Timber
import javax.inject.Inject

class ListTransactionFragment :BaseFragment<FragmentHomeBinding>(){

    companion object {
        fun newInstance(): ListTransactionFragment {
            var bundle = Bundle()
            var homeFragment = ListTransactionFragment()
            homeFragment.arguments = bundle
            return homeFragment
        }
    }

    private lateinit var listTransactionViewModel: ListTransactionViewModel

    override fun getlayoutId(): Int = R.layout.fragment_home

    @Inject lateinit var transactionRepository: TransactionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        listTransactionViewModel = ViewModelProviders.of(this,viewModelFactory).get(ListTransactionViewModel::class.java)
        listTransactionViewModel.listTransactionLiveData.observe(this, Observer {
            val list= it

            val adapter = viewDataBinding.recycleView.adapter as TransactionAdapter
            adapter?.let {
                adapter.swapListItem(list!!)
                viewDataBinding.recycleView.adapter = adapter
                return@Observer
            }
            adapter = TransactionAdapter()
            adapter.swapListItem(it!!)
            viewDataBinding.recycleView.adapter = adapter
        })
        listTransactionViewModel.getAllTransaction("0x81b7E08F65Bdf5648606c89998A9CC8164397647",0,99999999)
    }
    override fun onDestroy() {
        super.onDestroy()
    }

    private fun  initView(){
        viewDataBinding.recycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

    }
}