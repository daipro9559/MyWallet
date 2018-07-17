package com.example.dai_pc.android_test.view.main.transactions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.Toast
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
        listTransactionViewModel.getAllTransaction(0,99999999,true)
        listTransactionViewModel.listTransactionLiveData.observe(this, Observer {
            viewDataBinding.resource = it
            viewDataBinding.layoutLoading.txtDescription.text = "Loading list transaction"
            val list= it!!.t
            list?.let {
                val adapter = viewDataBinding.recycleView.adapter as TransactionAdapter
                adapter?.let {
                    adapter.swapListItem(list)
                    viewDataBinding.recycleView.adapter = adapter
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
            resfresh(false)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }

    private fun  initView(){
        val adapter = TransactionAdapter()
        adapter.addressMain = "0x9d60B3DE126D0cb84721956Be9AE4838BFA01211"
        viewDataBinding.recycleView.adapter = adapter

    }

    fun resfresh(isShowLoading:Boolean){
        listTransactionViewModel.getAllTransaction(0,99999999,isShowLoading)
    }

//    fun changeNetwork(id:Int){
//        transactionRepository.changeNetwork(id)
//
//    }
    fun changeAddress(address: String){
        val adapter = viewDataBinding.recycleView.adapter as TransactionAdapter
        adapter?.let{
            adapter.addressMain = address
        }
    }
}