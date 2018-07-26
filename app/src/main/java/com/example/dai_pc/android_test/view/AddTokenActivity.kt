package com.example.dai_pc.android_test.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseActivity
import com.example.dai_pc.android_test.databinding.ActivityAddTokenBinding
import com.example.dai_pc.android_test.view.main.token.TokenViewModel
import kotlinx.android.synthetic.main.activity_add_token.*
import timber.log.Timber

class AddTokenActivity : BaseActivity<ActivityAddTokenBinding>() {

    private lateinit var tokenViewModel: TokenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableHomeHomeAsUp()
        setTitle(R.string.add_token_title)
        tokenViewModel = ViewModelProviders.of(this, viewModelFactory)[TokenViewModel::class.java]
        btnSave.setOnClickListener {
            tokenViewModel.addToken(edtContractAddress.text.toString(),
                    edtSymbol.text.toString(),
                    edtDecimal.text.toString().toInt())
        }
        tokenViewModel.notifyAddCompleted.observe(this, Observer {
            Timber.e("add token state$it")
        })
    }

    override fun getLayoutId() = R.layout.activity_add_token
}