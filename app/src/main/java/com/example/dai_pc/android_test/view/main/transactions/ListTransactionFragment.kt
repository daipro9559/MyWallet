package com.example.dai_pc.android_test.view.main.transactions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.databinding.FragmentListTransactionBinding
import com.example.dai_pc.android_test.repository.TransactionRepository
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import com.example.dai_pc.android_test.view.main.MainActivity
import com.example.dai_pc.android_test.view.transaction.CreateTransactionActivity
import javax.inject.Inject

class ListTransactionFragment :BaseFragment<FragmentListTransactionBinding>(){

    companion object {
        fun newInstance(): ListTransactionFragment {
            var bundle = Bundle()
            var homeFragment = ListTransactionFragment()
            homeFragment.arguments = bundle
            return homeFragment
        }
    }

    private lateinit var listTransactionViewModel: ListTransactionViewModel

    override fun getlayoutId(): Int = R.layout.fragment_list_transaction

    @Inject lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        listTransactionViewModel = ViewModelProviders.of(this,viewModelFactory).get(ListTransactionViewModel::class.java)
        if (preferenceHelper.getString(getString(R.string.wallet_key)) ==null){
            viewDataBinding.floatButton.visibility = View.GONE
        }
        refresh(true)
        listTransactionViewModel.listTransactionLiveData.observe(this, Observer {
            viewDataBinding.resource = it
            viewDataBinding.layoutLoading.txtDescription.text = "Loading list transaction"
            val list= it!!.t
            list?.let {
                val adapter = viewDataBinding.recycleView.adapter as TransactionAdapter
                adapter?.let {
                    adapter.swapListItem(list)
                    return@Observer
                }
            }
            it.messError?.let {
                Toast.makeText(context,it,Toast.LENGTH_LONG).show()
            }
            if (viewDataBinding.refresh.isRefreshing) viewDataBinding.refresh.isRefreshing = false

        })
        viewDataBinding.floatButton.setOnClickListener {
            startActivity(CreateTransactionActivity::class.java)
        }

        viewDataBinding.refresh.setOnRefreshListener {
            refresh(false)
           (activity as MainActivity).loadBalance()

        }
    }

    fun checkHaveWallet() :Boolean{
        return if (preferenceHelper.getString(getString(R.string.wallet_key)) ==null){
            viewDataBinding.floatButton.visibility = View.GONE
            false
        }else{
            viewDataBinding.floatButton.visibility = View.VISIBLE
            true
        }
    }

    private fun  initView(){
        val adapter = TransactionAdapter()
        preferenceHelper.getString(getString(R.string.wallet_key))?.let{
            adapter.addressMain = it
        }
        viewDataBinding.recycleView.adapter = adapter

    }

    fun refresh(isShowLoading:Boolean){
         checkHaveWallet()

        listTransactionViewModel.getAllTransaction(0, 99999999, isShowLoading)

    }

    fun changeAddress(address: String){
        val adapter = viewDataBinding.recycleView.adapter as TransactionAdapter
        adapter?.let{
            adapter.addressMain = address
        }
    }
}