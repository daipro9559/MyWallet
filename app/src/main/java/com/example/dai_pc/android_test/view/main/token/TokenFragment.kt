package com.example.dai_pc.android_test.view.main.token

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.databinding.FragmentTokenBinding
import com.example.dai_pc.android_test.entity.Token
import com.example.dai_pc.android_test.view.AddTokenActivity
import com.example.dai_pc.android_test.view.transaction.CreateTransactionActivity
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
                // test
            }

        })
        refresh()
        tokenViewModel.valueBalance.observe(this, Observer {
            it?.let {
                tokenAdapter.updateBalance(it)
            }
        })
    }

    private fun initView() {
        viewDataBinding.recycleView.layoutManager
        tokenAdapter = TokenAdapter({ token, i -> tokenViewModel.getBalance(token, i) }
                , { token -> startSendTokenActivity(token) })
        viewDataBinding.recycleView.addItemDecoration(DividerItemDecoration(activity!!.applicationContext, LinearLayoutManager.VERTICAL))

        viewDataBinding.recycleView.adapter = tokenAdapter
        viewDataBinding.floatButtonAdd.setOnClickListener {
            startActivity(Intent(activity!!, AddTokenActivity::class.java))
        }
    }

    /**
     * temporary code : because case balance is loading and user click to token, balance is not define
     *
     */
    private fun startSendTokenActivity(token: Token) {
        val intent = Intent(activity!!, CreateTransactionActivity::class.java)
        intent.putExtra(Constant.IS_SEND_TOKEN, true)
        intent.putExtra(Constant.SYMBOL_TOKEN, token.symbol)
        intent.putExtra(Constant.BALANCE_TOKEN, token.balance)
        intent.putExtra(Constant.CONTRACT_ADDRESS, token.contractAddress)
        startActivity(intent)

    }

    fun refresh(){
        tokenViewModel.getAllToken()

    }


}