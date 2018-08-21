package com.example.dai_pc.android_test.view.main

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
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.Menu
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import com.example.dai_pc.android_test.view.accounts.ManageAccountFragment
import com.example.dai_pc.android_test.view.main.transactions.ListTransactionFragment
import com.example.dai_pc.android_test.view.setting.SettingActivity
import kotlinx.android.synthetic.main.toolbar_layout.*
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding>(), SharedPreferences.OnSharedPreferenceChangeListener {
    // check whe  change network or address
    private var isNeedReload :Boolean = false
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var drawerToggle: ActionBarDrawerToggle
    @Inject lateinit var preferenceHelper: PreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this)
        initView()
        mainViewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        replaceFragment(ListTransactionFragment.newInstance())
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
        drawerToggle = ActionBarDrawerToggle(this,viewDataBinding.drawerLayout,getToolbar(),R.string.drawer_open,R.string.drawer_close)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        drawerToggle.isDrawerIndicatorEnabled = true
        viewDataBinding.drawerLayout.addDrawerListener(drawerToggle)
        viewDataBinding.navView.setNavigationItemSelectedListener {
          navigationClickMenu(it)
        }
        toolBar.setTitle(R.string.my_account)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
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
        return true
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when (p1) {
            getString(R.string.network_key_eth) -> {
                isNeedReload = true
                changeNetwork(p0!!.getInt(getString(R.string.network_key_eth),1))
            }
            getString(R.string.account_select_eth_key) -> {
                isNeedReload = true
                changeWallet(p0!!.getString(getString(R.string.account_select_eth_key),""))
            }
            Constant.PLATFORM_KEY ->{
                reloadContent()
            }
        }
    }

    private fun reloadContent() {
    }

    private fun changeNetwork(id: Int){
        mainViewModel.changeNetwork(id)
    }

    private fun changeWallet(address:String){
        mainViewModel.changeAddress()
    }

    private fun navigationClickMenu(menuItem: MenuItem) : Boolean{
        when(menuItem.itemId){
            R.id.setting ->{
                startActivity(Intent(this,SettingActivity::class.java))
            }
            R.id.my_account ->{
                toolBar.setTitle(R.string.my_account)
                replaceFragment(ListTransactionFragment.newInstance())
            }
            R.id.manage_account ->{
                toolBar.setTitle(R.string.manage_account)
                replaceFragment(ManageAccountFragment.newInstance())
            }
        }
        menuItem.isCheckable = true
        viewDataBinding.drawerLayout.closeDrawer(Gravity.START)
        return true
    }

     fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(R.id.viewContainer,fragment)
                .disallowAddToBackStack()
                .commit()
    }

}