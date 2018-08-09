package com.example.dai_pc.android_test.view.main.transactions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import androidx.navigation.fragment.findNavController
import com.android.example.github.testing.OpenForTesting
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentListTransactionBinding
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import com.example.dai_pc.android_test.view.TRANSACTION_KEY
import com.example.dai_pc.android_test.view.TransactionDetailActivity
import com.example.dai_pc.android_test.view.main.MainActivity
import com.example.dai_pc.android_test.view.transaction.CreateTransactionActivity
import javax.inject.Inject

@OpenForTesting
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
        if (preferenceHelper.getString(getString(R.string.account_key)) ==null){
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

            if (viewDataBinding.refresh.isRefreshing) viewDataBinding.refresh.isRefreshing = false

        })
        viewDataBinding.floatButton.setOnClickListener {
            startActivity(CreateTransactionActivity::class.java)
        }

        viewDataBinding.refresh.setOnRefreshListener {
            refresh(false)
           (activity as MainActivity).loadBalance()

        }
        listTransactionViewModel.errorLiveData.observe(this, Observer {
            
        })
    }

    fun checkHaveWallet() :Boolean{
        return if (preferenceHelper.getString(getString(R.string.account_key)) ==null){
            viewDataBinding.floatButton.visibility = View.GONE
            false
        }else{
            viewDataBinding.floatButton.visibility = View.VISIBLE
            true
        }
    }

    private fun  initView(){
        val adapter = TransactionAdapter{
            val intent = Intent(activity!!,TransactionDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(TRANSACTION_KEY,it)
            intent.putExtra("bundle",bundle)
            startActivity(intent)
        }
        preferenceHelper.getString(getString(R.string.account_key))?.let{
            adapter.myWallet = it
        }
        viewDataBinding.recycleView.addItemDecoration(DividerItemDecoration(activity!!.applicationContext,LinearLayoutManager.VERTICAL))
        viewDataBinding.recycleView.adapter = adapter


    }

    fun refresh(isShowLoading:Boolean){
         checkHaveWallet()
        listTransactionViewModel.getAllTransaction(0, 99999999, isShowLoading)

    }

    fun changeAddress(address: String){
        viewDataBinding?.let {
            val adapter = viewDataBinding.recycleView.adapter as TransactionAdapter
            adapter?.let{
                adapter.myWallet = address
            }
        }

    }
    fun navigation() = findNavController()
}