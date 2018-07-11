package com.example.dai_pc.android_test.view.transaction

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentAddAddressReceiveBinding
import kotlinx.android.synthetic.main.fragment_add_address_receive.*
import org.walleth.activities.qrscan.QRScanActivity
import org.walleth.activities.qrscan.SCAN_REQUEST_CODE
import org.walleth.activities.qrscan.SCAN_RESULT
import org.walleth.activities.qrscan.startScanActivityForResult

class AddAddressReceiveFragment : BaseFragment<FragmentAddAddressReceiveBinding>(){

    companion object {
        const val TAG = "Address Receive"
        fun newInstance():AddAddressReceiveFragment{
            val bundle = Bundle()
            val addAddressReceiveFragment  =  AddAddressReceiveFragment()
            addAddressReceiveFragment.arguments = bundle
            return addAddressReceiveFragment
        }
    }

    override fun getlayoutId() = R.layout.fragment_add_address_receive

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnQRCode.setOnClickListener {
            startActivityForResult(Intent(activity,QRScanActivity::class.java), SCAN_REQUEST_CODE)
        }
        btnNext.setOnClickListener {
            val createTransactionActivity = activity as CreateTransactionActivity
            createTransactionActivity.addFragment(SendTransactionFragment.newInstance(txt_address.text.toString()),SendTransactionFragment.TAG,SendTransactionFragment.TAG)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==SCAN_REQUEST_CODE){
            data?.let {
                val scanResult = it.getStringExtra(SCAN_RESULT)
                txt_address.setText(scanResult)
            }

        }
    }
}