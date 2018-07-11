package com.example.dai_pc.android_test.view.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseActivity
import com.example.dai_pc.android_test.databinding.ActivityMainBinding
import android.view.MenuItem
import android.content.res.Configuration
import android.view.Menu
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.view.account.ListAccountFragment
import com.example.dai_pc.android_test.view.main.address.MyAddressFragment
import com.example.dai_pc.android_test.view.main.transactions.ListTransactionFragment
import com.example.dai_pc.android_test.view.setting.SettingActivity
import java.math.BigDecimal


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    private lateinit var mainViewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(viewDataBinding.toolBar)
        viewDataBinding.toolBar.title = "My Wallet"
        initView()
        mainViewModel = ViewModelProviders.of(this,viewModelFactory)[MainViewModel::class.java]
        mainViewModel.fetchBalance("0x312B416Af3159592bCae852278b17ced92f2d7dD")
        mainViewModel.balanceLiveData.observe(this, Observer {
            val data = BigDecimal(it,18)
            viewDataBinding.ether.text  = data.toFloat().toString()
        })
    }

    private fun initView() {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(ListTransactionFragment.newInstance())
        viewPagerAdapter.addFragment(MyAddressFragment.newInstance())
        viewPagerAdapter.addFragment(ListAccountFragment.newInstance())
        viewDataBinding.contentMain.viewPager.setCurrentItem(0, true)
        viewDataBinding.contentMain.tabLayout.setupWithViewPager(viewDataBinding.contentMain.viewPager)
        viewDataBinding.contentMain.viewPager.adapter = viewPagerAdapter
        setupTablayout()
    }
    private fun setupTablayout(){
        viewDataBinding.contentMain.tabLayout.getTabAt(0)!!.text = Constant.TRANSACTIONS
        viewDataBinding.contentMain.tabLayout.getTabAt(1)!!.text = Constant.MY_ADDRESS
        viewDataBinding.contentMain.tabLayout.getTabAt(2)!!.text = Constant.TEST
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.setting -> {
                startActivity(Intent(this,SettingActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }


}