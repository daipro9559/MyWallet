package com.example.dai_pc.android_test.view.main.token

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentTokenBinding
import com.example.dai_pc.android_test.view.AddTokenActivity
import timber.log.Timber

class TokenFragment : BaseFragment<FragmentTokenBinding>() {
    private lateinit var tokenAdapter: TokenAdapter
    private lateinit var tokenViewModel: TokenViewModel

    companion object {
        fun newInstance(): TokenFragment {
            val tokenFragment = TokenFragment()
            return tokenFragment
        }
    }

    override fun getlayoutId() = R.layout.fragment_token
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        tokenViewModel = ViewModelProviders.of(this, viewModelFactory)[TokenViewModel::class.java]
        tokenViewModel.listTokenInfo.observe(this, Observer {
            it?.let {
                Timber.e(it!!.size.toString())
                tokenAdapter.swapListItem(it)
            }

        })
        tokenViewModel.getAllToken()
    }

    private fun initView() {
        viewDataBinding.recycleView.layoutManager
        tokenAdapter = TokenAdapter()
        viewDataBinding.recycleView.addItemDecoration(DividerItemDecoration(activity!!.applicationContext, LinearLayoutManager.VERTICAL))

        viewDataBinding.recycleView.adapter = tokenAdapter
        viewDataBinding.floatButtonAdd.setOnClickListener {
            startActivity(Intent(activity!!, AddTokenActivity::class.java))
        }
    }


}