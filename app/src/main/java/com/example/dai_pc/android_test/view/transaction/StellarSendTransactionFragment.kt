package com.example.dai_pc.android_test.view.transaction

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.base.BaseViewModel
import com.example.dai_pc.android_test.databinding.FragmentStellarSendTransactionBinding
import com.example.dai_pc.android_test.entity.Resource
import kotlinx.android.synthetic.main.fragment_stellar_send_transaction.*
import org.walleth.activities.qrscan.QRScanActivity
import org.walleth.activities.qrscan.SCAN_REQUEST_CODE
import org.walleth.activities.qrscan.SCAN_RESULT

class StellarSendTransactionFragment : BaseFragment<FragmentStellarSendTransactionBinding>() {


    private lateinit var sendTransacViewModel: SendTransactionViewModel
    override fun getlayoutId() = R.layout.fragment_stellar_send_transaction
    private lateinit var progressAlertDialog : ProgressDialog
    companion object {
        const val TAG = "Send transaction"
        fun newInstance(): StellarSendTransactionFragment {
            val bundle = Bundle()
            val addAddressReceiveFragment = StellarSendTransactionFragment()
            addAddressReceiveFragment.arguments = bundle
            return addAddressReceiveFragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sendTransacViewModel = ViewModelProviders.of(this,viewModelFactory)[SendTransactionViewModel::class.java]
        btnQRCode.setOnClickListener {
            startActivityForResult(Intent(activity, QRScanActivity::class.java), SCAN_REQUEST_CODE)
        }
        buttonSend.setOnClickListener {
            sendTransacViewModel.sendTransactionStellar(txt_address.text.toString().trim(),amount.text.toString().toFloat(),"")
        }
        sendTransacViewModel.stellarSendTransactionData.observe(this, Observer {
            viewDataBinding.resource = it
            viewDataBinding.loadingLayout.txtDescription.text = getString(R.string.sending_transaction)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCAN_REQUEST_CODE) {
            data?.let {
                val scanResult = it.getStringExtra(SCAN_RESULT)
                txt_address.setText(scanResult)
            }
        }
    }
}