package com.example.dai_pc.android_test.view.transaction

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseActivity
import com.example.dai_pc.android_test.databinding.ActivityCreateTransactionBinding


class CreateTransactionActivity :BaseActivity<ActivityCreateTransactionBinding>(){

    override fun getLayoutId() = R.layout.activity_create_transaction
    private lateinit var createTransactionViewModel: SendTransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(viewDataBinding.toolBar)
        enableHomeHomeAsUp()
        createTransactionViewModel = ViewModelProviders.of(this,viewModelFactory).get(SendTransactionViewModel::class.java)
        addFragment(AddAddressReceiveFragment.newInstance(),AddAddressReceiveFragment.TAG,AddAddressReceiveFragment.TAG)

    }

    fun addFragment(fragment : Fragment,tag:String,title:String){
        supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.viewContainer,fragment!!,tag)
                .addToBackStack(tag)
                .commit()
        setTitle(title)
    }

    override fun onBackPressed() {
        var  cout = supportFragmentManager.backStackEntryCount
        if (supportFragmentManager.backStackEntryCount==1){
            finish()
            return
        }
        super.onBackPressed()
    }


}