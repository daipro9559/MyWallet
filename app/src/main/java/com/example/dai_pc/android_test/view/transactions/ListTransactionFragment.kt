package com.example.dai_pc.android_test.view.transactions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentListTransactionBinding
import com.example.dai_pc.android_test.repository.TransactionRepository
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
        })
        listTransactionViewModel.getAllTransaction("0x6480600bad47cB4D2d1E827592e199886Fd5fb3a",0,99999999)
        viewDataBinding.floatButton.setOnClickListener {
            startActivity(CreateTransactionActivity::class.java)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }

    private fun  initView(){
        viewDataBinding.recycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val adapter = TransactionAdapter()
        adapter.addressMain = "0x6480600bad47cB4D2d1E827592e199886Fd5fb3a"
        viewDataBinding.recycleView.adapter = adapter


    }
}