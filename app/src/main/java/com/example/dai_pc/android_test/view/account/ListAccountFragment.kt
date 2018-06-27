package com.example.dai_pc.android_test.view.account

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentListAccountBinding
import com.example.dai_pc.android_test.repository.WalletRepository
import timber.log.Timber
import javax.inject.Inject

class ListAccountFragment : BaseFragment<FragmentListAccountBinding>() {

    @Inject
    lateinit var walletRepository: WalletRepository

    companion object {
        fun newInstance(): ListAccountFragment {
            var bundle = Bundle()
            var listAccountFragment = ListAccountFragment()
            listAccountFragment.arguments = bundle
            return listAccountFragment
        }
    }

    override fun getlayoutId(): Int = R.layout.fragment_list_account
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        walletRepository.accountsLiveData.observe(this, Observer {
            it.let {
               var adapter = viewDataBinding.recycleView.adapter as AccountAdapter
                adapter.swapListItem(it!!)
            }
        })
        walletRepository.getAllAccount()
        viewDataBinding.buttonAddAccount.setOnClickListener { addAccount() }

    }

    fun addAccount() {
        walletRepository.createAccount()
    }

    fun initView(){
        var accountAdapter  = AccountAdapter()
       viewDataBinding.recycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        viewDataBinding.recycleView.adapter = accountAdapter
    }
}