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


class MainActivity : BaseActivity<ActivityMainBinding>() {
    // check whe  change network or address
    private var isNeedReload :Boolean = false
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
    private lateinit var drawerToggle: ActionBarDrawerToggle
    @Inject lateinit var preferenceHelper: PreferenceHelper
    private lateinit var menuItem : MenuItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
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
        menuItem = viewDataBinding.navView.menu.findItem(R.id.my_account)

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

     fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(R.id.viewContainer,fragment)
                .disallowAddToBackStack()
                .commit()
    }

}