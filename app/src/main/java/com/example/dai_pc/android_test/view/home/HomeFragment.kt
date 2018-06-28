package com.example.dai_pc.android_test.view.home

import android.os.Bundle
import com.example.dai_pc.android_test.AppExecutors
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentHomeBinding
import com.example.dai_pc.android_test.repository.TransactionRepository
import com.example.dai_pc.android_test.ultil.BalanceUltil
import com.example.dai_pc.android_test.view.account.ListAccountFragment
import io.reactivex.functions.Consumer
import okhttp3.OkHttpClient
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import timber.log.Timber
import javax.inject.Inject

class HomeFragment :BaseFragment<FragmentHomeBinding>(){

    companion object {
        fun newInstance(): HomeFragment{
            var bundle = Bundle()
            var homeFragment = HomeFragment()
            homeFragment.arguments = bundle
            return homeFragment
        }
    }
    // test
    @Inject lateinit var okHttpClient: OkHttpClient

    @Inject lateinit var appExcutor : AppExecutors
    override fun getlayoutId(): Int = R.layout.fragment_home

    @Inject lateinit var transactionRepository: TransactionRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        appExcutor.networkIO().execute{
           val balance = Web3jFactory
                    .build(HttpService("https://ropsten.infura.io/llyrtzQ3YhkdESt2Fzrk", okHttpClient, false))
                    .ethGetBalance("0xf3AF96F89b3d7CdcBE0C083690A28185Feb0b3CE", DefaultBlockParameterName.LATEST)
                    .send()
                    .balance
            appExcutor.mainThread().execute{
                viewDataBinding.txtBalance.text = BalanceUltil.weiToEth(balance,5) + "ETH"
            }
        }

        transactionRepository.fetchTransaction("0xf3AF96F89b3d7CdcBE0C083690A28185Feb0b3CE")
                .subscribe({ t -> Timber.e(""+t.size) },
                        { t -> Timber.e(t.message)  })

    }
    override fun onDestroy() {
        super.onDestroy()
    }
}