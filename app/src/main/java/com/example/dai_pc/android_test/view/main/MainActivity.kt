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
import android.os.PersistableBundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.View
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.view.main.address.MyAddressFragment
import com.example.dai_pc.android_test.view.main.token.TokenFragment
import com.example.dai_pc.android_test.view.main.transactions.ListTransactionFragment
import com.example.dai_pc.android_test.view.rate.RateActivity
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
    private lateinit var drawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this)
        initView()
        mainViewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        loadBalance()
        mainViewModel.balanceLiveData.observe(this, Observer {
            it!!.t?.let {
                viewDataBinding.contentMain.etherTitle.visibility  = View.VISIBLE
                val data = BigDecimal(it, 18)
                viewDataBinding.contentMain.ether.text = data.toFloat().toString()
            }
            it.messError?.let {
                viewDataBinding.contentMain.ether.text = "----"
                viewDataBinding.contentMain.etherTitle.visibility  = View.VISIBLE

            }
        })
        viewDataBinding.contentMain.viewBalance.setOnClickListener {
            startActivity(Intent(this,RateActivity::class.java))
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }
    private fun initView() {
        viewDataBinding.contentMain.viewPager.offscreenPageLimit = 3
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(ListTransactionFragment.newInstance())
        viewPagerAdapter.addFragment(MyAddressFragment.newInstance())
        viewPagerAdapter.addFragment(TokenFragment.newInstance())
        viewDataBinding.contentMain.viewPager.setCurrentItem(0, true)
        viewDataBinding.contentMain.tabLayout.setupWithViewPager(viewDataBinding.contentMain.viewPager)
        viewDataBinding.contentMain.viewPager.adapter = viewPagerAdapter
        setupTablayout()
        (viewPagerAdapter.getItem(1) as MyAddressFragment).callback = {
            reloadContent()
        }
        drawerToggle = ActionBarDrawerToggle(this,viewDataBinding.drawerLayout,getToolbar(),R.string.drawer_open,R.string.drawer_close)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        drawerToggle.isDrawerIndicatorEnabled = true
        viewDataBinding.drawerLayout.addDrawerListener(drawerToggle)
    }

    private fun setupTablayout() {
        viewDataBinding.contentMain.tabLayout.getTabAt(0)!!.text = Constant.TRANSACTIONS
        viewDataBinding.contentMain.tabLayout.getTabAt(1)!!.text = Constant.MY_ACCOUNT
        viewDataBinding.contentMain.tabLayout.getTabAt(2)!!.text = Constant.MY_TOKEN
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
            getString(R.string.account_key) -> {
                isNeedReload = true
                changeWallet(p0!!.getString(getString(R.string.account_key),""))
            }
        }
    }

    private fun reloadContent() {
       loadBalance()
        val fragmentTransaction = viewPagerAdapter.getItem(0) as ListTransactionFragment
        fragmentTransaction?.let {
            it.refresh(true)
        }
        val fragmentMyAddress= viewPagerAdapter.getItem(1) as MyAddressFragment
        fragmentMyAddress?.let {
            it.refresh()
        }
        val tokenFragment = viewPagerAdapter.getItem(2) as TokenFragment
        tokenFragment?.let {
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
    fun loadBalance(){
        mainViewModel.fetchBalance()
    }
}