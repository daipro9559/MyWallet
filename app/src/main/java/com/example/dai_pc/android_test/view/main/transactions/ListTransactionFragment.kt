package com.example.dai_pc.android_test.view.main.transactions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.android.example.github.testing.OpenForTesting
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.customview.Identicon
import com.example.dai_pc.android_test.databinding.FragmentListTransactionBinding
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import com.example.dai_pc.android_test.view.TRANSACTION_KEY
import com.example.dai_pc.android_test.view.TransactionDetailActivity
import com.example.dai_pc.android_test.view.main.MainActivity
import com.example.dai_pc.android_test.view.main.address.MyAddressFragment
import com.example.dai_pc.android_test.view.transaction.CreateTransactionActivity
import java.math.BigDecimal

import javax.inject.Inject

@OpenForTesting
class ListTransactionFragment : BaseFragment<FragmentListTransactionBinding>() {

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

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private lateinit var platform: String
    private lateinit var etherTransactionAdapter: TransactionAdapter
    private lateinit var stellarTransactionAdapter: StellarTransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        platform = preferenceHelper.getPlatform()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        listTransactionViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListTransactionViewModel::class.java)
        if (preferenceHelper.getString(getString(R.string.account_select_eth_key)) == null) {
            viewDataBinding.fabMenu.visibility = View.GONE
        }
        listTransactionViewModel.listTransactionEther.observe(this, Observer {
            viewDataBinding.recycleView.adapter = etherTransactionAdapter
            viewDataBinding.resource = it
            viewDataBinding.layoutLoading.txtDescription.text = "Loading list transaction"
            val list = it!!.t
            list?.let {
                etherTransactionAdapter.swapListItem(list)
                return@Observer
            }
        })
        listTransactionViewModel.listTransactionStellar.observe(this, Observer {
            viewDataBinding.recycleView.adapter = stellarTransactionAdapter
            viewDataBinding.resource = it
            viewDataBinding.layoutLoading.txtDescription.text = "Loading list transaction"
            it!!.t?.let {
                val id = it[0].sourceAccount.accountId
                stellarTransactionAdapter.swapListItem(it!!)
            }
        })
        listTransactionViewModel.balanceEther.observe(this, Observer {
            if (it!!.status == Resource.Status.LOADING){
                viewDataBinding.txtBalance.text="..."
                viewDataBinding.txtBalance.startAnimation(AnimationUtils.loadAnimation(activity!!.applicationContext,R.anim.title_fade))
            }else{
                viewDataBinding.txtBalance.clearAnimation()

            }
            it?.t?.let {
                val data = BigDecimal(it.toBigIntegerOrNull(),18).toFloat().toString()
                viewDataBinding.txtBalance.text = data.toFloat().toString() + " ETH"
            }
        })
        listTransactionViewModel.accountLiveData.observe(this, Observer {
            if (it == null){
                return@Observer
            }
            viewDataBinding.txtAccount.text = it
            listTransactionViewModel.getAllTransaction()
            listTransactionViewModel.getBalance()
        })
    }

    fun checkHaveWallet(): Boolean {
        return if (preferenceHelper.getString(getString(R.string.account_select_eth_key)) == null) {
            viewDataBinding.fabMenu.visibility = View.GONE
            false
        } else {
            viewDataBinding.fabMenu.visibility = View.VISIBLE
            true
        }
    }

    private fun initView() {
        viewDataBinding.buttonSend.setOnClickListener {
            startActivity(CreateTransactionActivity::class.java)
        }
        viewDataBinding.buttonReceive.setOnClickListener {
            (activity!! as MainActivity).replaceFragment(MyAddressFragment.newInstance())
        }
        stellarTransactionAdapter = StellarTransactionAdapter()
        etherTransactionAdapter = TransactionAdapter {
            val intent = Intent(activity!!, TransactionDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(TRANSACTION_KEY, it)
            intent.putExtra("bundle", bundle)
            startActivity(intent)
        }
        preferenceHelper.getString(getString(R.string.account_select_eth_key))?.let {
            etherTransactionAdapter.myWallet = it
//            val byteArray = Base64.decode(imageString)
//            val  bitmap = BitmapFactory.decodeByteArray(byteArray,0,imageString.length)
//            viewDataBinding.iconAccount.setImageBitmap(bitmap)
        }
        viewDataBinding.recycleView.layoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        viewDataBinding.iconAccount.setImageBitmap(Identicon.create("1"))
    }

        fun refresh(isShowLoading: Boolean) {
            checkHaveWallet()
            listTransactionViewModel.getAllTransaction()
            listTransactionViewModel.getBalance()
            listTransactionViewModel.getAccount()
        }

        fun changeAddress(address: String) {
            if (platform == Constant.ETHEREUM_PLATFORM) {
                etherTransactionAdapter?.let {
                    etherTransactionAdapter.myWallet = address
                }
            } else if (platform == Constant.STELLAR_PLATFORM) {
                stellarTransactionAdapter?.let {
                    stellarTransactionAdapter.myAccount = address
                }
            }
        }

        fun navigation() = findNavController()
    }