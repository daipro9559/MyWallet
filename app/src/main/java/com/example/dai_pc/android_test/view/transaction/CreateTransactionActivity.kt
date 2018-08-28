package com.example.dai_pc.android_test.view.transaction

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseActivity
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.databinding.ActivityCreateTransactionBinding
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject


class CreateTransactionActivity : BaseActivity<ActivityCreateTransactionBinding>() {

    override fun getLayoutId() = R.layout.activity_create_transaction
    private lateinit var createTransactionViewModel: SendTransactionViewModel
    @Inject lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.send_transaction)
        enableHomeHomeAsUp()
        createTransactionViewModel = ViewModelProviders.of(this, viewModelFactory).get(SendTransactionViewModel::class.java)
        handleIntent()
    }

    private fun handleIntent() {
         if (intent.getBooleanExtra(Constant.IS_SEND_TOKEN, false)) {
            addFragment(SendTransactionFragment.newInstance(intent.getBooleanExtra(Constant.IS_SEND_TOKEN, false), intent.getStringExtra(Constant.SYMBOL_TOKEN),  intent.getStringExtra(Constant.CONTRACT_ADDRESS))
                    , SendTransactionFragment.TAG
            )
        } else {
             if (preferenceHelper.getPlatform() == Constant.ETHEREUM_PLATFORM) {
                 addFragment(SendTransactionFragment.newInstance(), SendTransactionFragment.TAG)
             }else{
                 addFragment(StellarSendTransactionFragment.newInstance(), StellarSendTransactionFragment.TAG)
             }
        }
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.viewContainer, fragment!!, tag)
                .disallowAddToBackStack()
                .commit()
    }
}