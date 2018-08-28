package com.example.dai_pc.android_test.view.main.transactions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.opengl.Visibility
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
import com.example.dai_pc.android_test.view.main.MainFragment
import com.example.dai_pc.android_test.view.main.address.MyAddressFragment
import com.example.dai_pc.android_test.view.transaction.CreateTransactionActivity
import timber.log.Timber
import java.math.BigDecimal

import javax.inject.Inject

@OpenForTesting
class ListTransactionFragment : MainFragment<FragmentListTransactionBinding>() {


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
            if (it!!.status == Resource.Status.LOADING) {
                viewDataBinding.txtBalance.text = "..."
                viewDataBinding.txtBalance.startAnimation(AnimationUtils.loadAnimation(activity!!.applicationContext, R.anim.title_fade))
            } else {
                viewDataBinding.txtBalance.clearAnimation()

            }
            it?.t?.let {
                val data = BigDecimal(it.toBigIntegerOrNull(), 18).toFloat().toString()
                viewDataBinding.txtBalance.text = data.toFloat().toString() + " ETH"
            }
        })
        listTransactionViewModel.accountLiveData.observe(this, Observer {
            if (it == null) {
                viewDataBinding.txtAccount.text = getString(R.string.no_account)
                viewDataBinding.iconAccount.visibility = View.GONE
                viewDataBinding.fabMenu.visibility = View.GONE
                return@Observer
            }
            viewDataBinding.txtAccount.text = it
            listTransactionViewModel.getAllTransaction()
            listTransactionViewModel.getBalance()
        })

        listTransactionViewModel.balanceStellar.observe(this, Observer {
            it!!.t?.let {
                var  balanceData = String()
                for (balance in it){
                    balanceData = "Balance: "+ balance.balance + "XML; Type: " + balance.assetType +"; Code: "+ balance.assetCode +"\n"
                }
                viewDataBinding.txtBalance.text = balanceData
            }
        })


    }

    fun checkHaveWallet(): Boolean {
        return if (preferenceHelper.getString(Constant.ACCOUNT_ETHEREUM_KEY) == null) {
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
            (activity!! as MainActivity).replaceFragmentWithBackStack(MyAddressFragment.newInstance())
        }
        stellarTransactionAdapter = StellarTransactionAdapter()
        etherTransactionAdapter = TransactionAdapter {
            val intent = Intent(activity!!, TransactionDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(TRANSACTION_KEY, it)
            intent.putExtra("bundle", bundle)
            startActivity(intent)
        }
        preferenceHelper.getString(Constant.ACCOUNT_ETHEREUM_KEY)?.let {
            etherTransactionAdapter.myWallet = it
//            val byteArray = Base64.decode(imageString)
//            val  bitmap = BitmapFactory.decodeByteArray(byteArray,0,imageString.length)
//            viewDataBinding.iconAccount.setImageBitmap(bitmap)
        }
        viewDataBinding.recycleView.layoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        viewDataBinding.iconAccount.setImageBitmap(Identicon.create("1"))
    }

    fun refresh(isShowLoading: Boolean) {

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

    override fun refresh() {
        checkHaveWallet()
    }

    override fun changeNetwork() {
        resetView()
        listTransactionViewModel.changeNetwork()
        listTransactionViewModel.refreshAll()
    }

    override fun changeAccount() {
        resetView()
        listTransactionViewModel.changeAccount()
        if (platform == Constant.ETHEREUM_PLATFORM) {
            preferenceHelper.getString(Constant.ACCOUNT_ETHEREUM_KEY)?.let {
                etherTransactionAdapter.myWallet = preferenceHelper.getString(Constant.ACCOUNT_ETHEREUM_KEY)
            }
            if (preferenceHelper.getString(Constant.ACCOUNT_ETHEREUM_KEY ) == null){
                viewDataBinding.txtAccount.text = getString(R.string.no_account)
                viewDataBinding.iconAccount.visibility = View.GONE

            }else{
                viewDataBinding.txtAccount.text = preferenceHelper.getString(Constant.ACCOUNT_ETHEREUM_KEY)
                viewDataBinding.iconAccount.visibility = View.VISIBLE
                viewDataBinding.fabMenu.visibility = View.VISIBLE
            }
        } else if (platform == Constant.STELLAR_PLATFORM) {
            stellarTransactionAdapter?.let {
                //                etherTransactionAdapter.myWallet = preferenceHelper.getString(Constant.ACCOUNT_STELLAR_KEY)
            }

            if (preferenceHelper.getString(Constant.ACCOUNT_STELLAR_KEY) == null){
                viewDataBinding.txtAccount.text = getString(R.string.no_account)
                viewDataBinding.iconAccount.visibility = View.GONE

            }else{
                viewDataBinding.txtAccount.text = preferenceHelper.getString(Constant.ACCOUNT_STELLAR_KEY)
                viewDataBinding.iconAccount.visibility = View.VISIBLE
                viewDataBinding.fabMenu.visibility = View.VISIBLE
            }
        }
        listTransactionViewModel.refreshAll()
    }

    override fun changePlatform() {
        platform = preferenceHelper.getPlatform()
        resetView()
        changeAccount()
        changeNetwork()
        listTransactionViewModel.refreshAll()

    }

    // reset view after change account, network
    private fun resetView(){
        viewDataBinding.txtBalance.text = "...."
        if (platform == Constant.ETHEREUM_PLATFORM){
            viewDataBinding.recycleView.adapter = etherTransactionAdapter
            etherTransactionAdapter.swapListItem(listOf())
        }else{
            viewDataBinding.recycleView.adapter = stellarTransactionAdapter
            stellarTransactionAdapter.swapListItem(listOf())
        }
    }
}