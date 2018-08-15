package com.example.dai_pc.android_test.view

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseActivity
import com.example.dai_pc.android_test.databinding.ActivityTransactionDetailBinding
import com.example.dai_pc.android_test.entity.Transaction
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import com.example.dai_pc.android_test.ultil.Ultil
import java.math.BigDecimal
import javax.inject.Inject

const  val TRANSACTION_KEY = "transaction_intent_key"

class TransactionDetailActivity : BaseActivity<ActivityTransactionDetailBinding>(){

    @Inject lateinit var preferenceHelper: PreferenceHelper
    override fun getLayoutId() = R.layout.activity_transaction_detail

    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableHomeHomeAsUp()
        setTitle(R.string.transaction_detail)
        val transaction = intent.getBundleExtra("bundle").getParcelable(TRANSACTION_KEY) as Transaction

        transaction?.let {
            if (it.from.endsWith(preferenceHelper.getString(getString(R.string.account_select_eth_key)),true))  {
                viewDataBinding.txtDescription.text = resources.getString(R.string.send_ether,BigDecimal(it.value.toBigIntegerOrNull(),18).toFloat().toString())
            }else{
                viewDataBinding.txtDescription.text = resources.getString(R.string.receive_ether,BigDecimal(it.value.toBigIntegerOrNull(),18).toFloat().toString())

            }
            viewDataBinding.from.text = it.from
            viewDataBinding.to.text = it.to
            viewDataBinding.amount.text = BigDecimal(it.value.toBigIntegerOrNull(),18).toFloat().toString() + " ETH"
            viewDataBinding.gasUsed.text = it.gasUsed
            viewDataBinding.time.text = Ultil.getTimeFromTimeStamp(it.timeStamp!!.toLong(),this)
            viewDataBinding.nonce.text = it.nonce
            viewDataBinding.block.text = it.blockNumber.toString()

        }
    }
}