package com.example.dai_pc.android_test.view.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseActivity
import com.example.dai_pc.android_test.databinding.ActivityMainBinding
import dagger.android.AndroidInjector
import android.R.id.toggle
import android.view.MenuItem
import android.R.id.toggle
import android.R.id.toggle
import android.content.Intent
import android.content.res.Configuration
import android.support.design.widget.NavigationView
import android.view.Gravity
import com.example.dai_pc.android_test.view.account.ListAccountFragment
import com.example.dai_pc.android_test.view.home.HomeFragment
import com.example.dai_pc.android_test.view.network.NetworkFragment
import com.example.dai_pc.android_test.view.transaction.CreateTransactionActivity


class MainActivity:BaseActivity<ActivityMainBinding>(){

   lateinit var actionBarDrawerToggle:ActionBarDrawerToggle
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,viewDataBinding.drawLayout,R.string.drawer_open,R.string.drawer_close)
        setSupportActionBar(viewDataBinding.toolBar)
        viewDataBinding.toolBar.title = "My Wallet"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        viewDataBinding.drawLayout.addDrawerListener(actionBarDrawerToggle)
        viewDataBinding.navigation.setNavigationItemSelectedListener {
            selectItemMenu(it)
        }
        viewDataBinding.createTransaction.setOnClickListener {

            startActivity(Intent(this,CreateTransactionActivity::class.java))
        }
    }

   override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return actionBarDrawerToggle != null && actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (actionBarDrawerToggle != null)
           actionBarDrawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (actionBarDrawerToggle != null)
            actionBarDrawerToggle.onConfigurationChanged(newConfig)
    }

    fun selectItemMenu(item:MenuItem):Boolean{
        when(item.itemId){
            R.id.choose_address->{
                replaceFragment(ListAccountFragment.newInstance())

            }
            R.id.home ->{
                replaceFragment(HomeFragment.newInstance())

            }
            R.id.select_network ->{
                replaceFragment(NetworkFragment.newInstance())

            }
        }
        viewDataBinding.drawLayout.closeDrawer(Gravity.START)
        return true
    }

    fun <F : Fragment>replaceFragment(fragment : F){
        supportFragmentManager.beginTransaction().replace(R.id.view_container,fragment).disallowAddToBackStack().commit()
    }
}