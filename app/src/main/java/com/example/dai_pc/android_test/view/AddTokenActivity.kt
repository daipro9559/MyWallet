package com.example.dai_pc.android_test.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseActivity
import com.example.dai_pc.android_test.databinding.ActivityAddTokenBinding
import com.example.dai_pc.android_test.ultil.Ultil
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
            if (validateInput()) {
                return@setOnClickListener
            }
            tokenViewModel.addToken(edtContractAddress.text.toString().trim(),
                    edtSymbol.text.toString(),
                    edtDecimal.text.toString().toInt())
        }
        tokenViewModel.notifyAddCompleted.observe(this, Observer {
            if (it!!.compareTo(-1)==0){
                Ultil.showDialogNotify(this,getString(R.string.error),getString(R.string.add_token_fail)){

                }
            }else{
                Toast.makeText(applicationContext,getString(R.string.add_token_completed),Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    override fun getLayoutId() = R.layout.activity_add_token
    private fun validateInput(): Boolean {
        var isInvalid = false
        if (Ultil.checkAddressPattern(edtContractAddress.text.toString().trim())) {
            edtContractAddress.error = getString(R.string.error_invalid_address)
            isInvalid = true
        }
        if (TextUtils.isEmpty(edtDecimal.text.toString())){
            edtDecimal.error = getString(R.string.field_require)
            isInvalid = true
        }

        if (TextUtils.isEmpty(edtSymbol.text.toString())){
            edtSymbol.error = getString(R.string.field_require)
            isInvalid = true
        }

        return isInvalid
    }
}