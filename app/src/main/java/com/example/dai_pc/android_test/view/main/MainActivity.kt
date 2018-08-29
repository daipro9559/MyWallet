package com.example.dai_pc.android_test.view.main

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
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.Gravity
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.repository.NetworkRepository
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import com.example.dai_pc.android_test.view.main.accounts.ManageAccountFragment
import com.example.dai_pc.android_test.view.main.transactions.ListTransactionFragment
import com.example.dai_pc.android_test.view.setting.SettingActivity
import kotlinx.android.synthetic.main.toolbar_layout.*
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding>(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when (p1) {
            Constant.KEY_NETWORK_STELLAR -> {
                txtNetwork.text = if (preferenceHelper.getString(Constant.KEY_NETWORK_STELLAR) == Constant.STELLAR_MAIN_NET_URl) getString(R.string.main_net) else getString(R.string.test_net)

            }
            Constant.KEY_NETWORK_ETHER-> {
                val networkProvider = NetworkRepository.listNetWorkProvier.firstOrNull{
                    it.id == p0!!.getInt(Constant.KEY_NETWORK_ETHER,1)
                }
                txtNetwork.text = networkProvider!!.name
            }
            Constant.PLATFORM_KEY -> initHeaderNav()
        }
    }

    private lateinit var sharedPreferences: SharedPreferences
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
    private lateinit var drawerToggle: ActionBarDrawerToggle
    @Inject lateinit var preferenceHelper: PreferenceHelper
    private lateinit var menuItem : MenuItem
    private lateinit var imgIcon :AppCompatImageView
    private lateinit var txtPlatform: AppCompatTextView
    private lateinit var txtNetwork : AppCompatTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        replaceFragment(ListTransactionFragment.newInstance())
       sharedPreferences = getSharedPreferences(getString(R.string.app_name),Context.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
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
        menuItem = viewDataBinding.navView.menu.findItem(R.id.my_account)
        initHeaderNav()
    }

    private fun initHeaderNav(){
        imgIcon = viewDataBinding.navView.getHeaderView(0).findViewById(R.id.imgIcon)
        txtPlatform = viewDataBinding.navView.getHeaderView(0).findViewById(R.id.txtPlatform)
        txtNetwork = viewDataBinding.navView.getHeaderView(0).findViewById(R.id.txtNetwork)
        if (preferenceHelper.getPlatform() == Constant.ETHEREUM_PLATFORM) {
            imgIcon.setImageResource(R.drawable.ic_eth)
            txtPlatform.text = getString(R.string.ethereum)
            val networkProvider = NetworkRepository.listNetWorkProvier.firstOrNull{
                it.id == preferenceHelper.getInt(Constant.KEY_NETWORK_ETHER,1)
            }
            txtNetwork.text = networkProvider!!.name
        }else if (preferenceHelper.getPlatform() == Constant.STELLAR_PLATFORM){
            imgIcon.setImageResource(R.drawable.ic_stellar)
            txtPlatform.text = getString(R.string.stellar)
            txtNetwork.text = if (preferenceHelper.getString(Constant.KEY_NETWORK_STELLAR) == Constant.STELLAR_MAIN_NET_URl) getString(R.string.main_net) else getString(R.string.test_net)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        sharedPreferences
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
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

        if (menuItem.itemId != R.id.setting){
            this.menuItem = menuItem
        }
        viewDataBinding.navView.setCheckedItem(this.menuItem.itemId)
        viewDataBinding.drawerLayout.closeDrawer(Gravity.START)
        return false
    }

     private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(R.id.viewContainer,fragment)
                .disallowAddToBackStack()
                .commit()
    }
    fun replaceFragmentWithBackStack(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(R.id.viewContainer,fragment)
                .addToBackStack("")
                .commit()
    }

}