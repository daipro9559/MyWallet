package com.example.dai_pc.android_test.view.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseActivity
import com.example.dai_pc.android_test.databinding.ActivityMainBinding
import android.view.MenuItem
import android.content.res.Configuration
import android.view.Menu
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.view.main.address.MyAddressFragment
import com.example.dai_pc.android_test.view.main.transactions.ListTransactionFragment
import com.example.dai_pc.android_test.view.setting.SettingActivity
import java.math.BigDecimal


class MainActivity : BaseActivity<ActivityMainBinding>(), SharedPreferences.OnSharedPreferenceChangeListener {
    // check whe  change network or address
    private var isNeedReload :Boolean = false
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this)
        setSupportActionBar(viewDataBinding.toolBar)
        viewDataBinding.toolBar.title = "My Wallet"
        initView()
        mainViewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        mainViewModel.fetchBalance()
        mainViewModel.balanceLiveData.observe(this, Observer {
            it!!.t?.let {
                val data = BigDecimal(it, 18)
                viewDataBinding.ether.text = data.toFloat().toString()
            }
        })
    }

    private fun initView() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(ListTransactionFragment.newInstance())
        viewPagerAdapter.addFragment(MyAddressFragment.newInstance())
        viewDataBinding.contentMain.viewPager.setCurrentItem(0, true)
        viewDataBinding.tabLayout.setupWithViewPager(viewDataBinding.contentMain.viewPager)
        viewDataBinding.contentMain.viewPager.adapter = viewPagerAdapter
        setupTablayout()
        (viewPagerAdapter.getItem(1) as MyAddressFragment).callback = {
            reloadContent()
        }
    }

    private fun setupTablayout() {
        viewDataBinding.tabLayout.getTabAt(0)!!.text = Constant.TRANSACTIONS
//        viewDataBinding.contentMain.tabLayout.getTabAt(0)!!.icon = getDrawable(R.drawable.ic_transaction)
        viewDataBinding.tabLayout.getTabAt(1)!!.text = Constant.MY_ADDRESS
//        viewDataBinding.contentMain.tabLayout.getTabAt(2)!!.text = Constant.TEST
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (isNeedReload){
            reloadContent()
            isNeedReload = false
        }
    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when (p1) {
            getString(R.string.network_key) -> {
                isNeedReload = true
                changeNetwork(p0!!.getInt(getString(R.string.network_key),1))
            }
            getString(R.string.wallet_key) -> {
                isNeedReload = true
                changeWallet(p0!!.getString(getString(R.string.wallet_key),""))
            }
        }
    }

    private fun reloadContent() {
        mainViewModel.fetchBalance()
        val fragmentTransaction = viewPagerAdapter.getItem(0) as ListTransactionFragment
        fragmentTransaction?.let {
            it.resfresh(true)
        }
        val fragmentMyAddress= viewPagerAdapter.getItem(1) as MyAddressFragment
        fragmentMyAddress?.let {
            it.refresh()
        }
    }
    private fun changeNetwork(id: Int){
        mainViewModel.changeNetwork(id)
    }

    private fun changeWallet(address:String){
        mainViewModel.changeAddress()
        val fragmentTransaction = viewPagerAdapter.getItem(0) as ListTransactionFragment
        fragmentTransaction?.let {
            it.changeAddress(address)
        }
    }
}