package com.example.dai_pc.android_test.view.transaction

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.Toast
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentSendTransactionBinding
import com.example.dai_pc.android_test.entity.TransactionSendedObject
import com.example.dai_pc.android_test.ultil.BalanceUltil
import kotlinx.android.synthetic.main.fragment_send_transaction.*
import java.math.BigInteger

class SendTransactionFragment :BaseFragment<FragmentSendTransactionBinding>(){
    override fun getlayoutId() = R.layout.fragment_send_transaction
    lateinit var createTransactionViewModel: CreateTransactionViewModel
    companion object {
        const val TAG = "Send transaction"
        fun newInstance(toAddress:String):SendTransactionFragment{
            val bundle = Bundle()
            bundle.putString("address_target",toAddress)
            val sendTransactionFragment  =  SendTransactionFragment()
            sendTransactionFragment.arguments = bundle
            return sendTransactionFragment
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.txtAddressTarget.text = arguments!!.getString("address_target")
        createTransactionViewModel = ViewModelProviders.of(this,viewModelFactory)[CreateTransactionViewModel::class.java]
        //        var transactionSendedObject = TransactionSendedObject.Builder()
//                .setFrom("0x3b694a36935f96638118b7b17c0afb92850ed985")
//                .setTo("0xB36001A1d3054A1966897Aa5d869D9180ed090A1")
//                .setAmount(BalanceUltil.baseToSubunit("0", 18))// 18 is Ether_decimals
//                .setGasPrice(BigInteger.valueOf(1000000000L))
//                .setGasLimit(BigInteger.valueOf(21000L))
//                .build()
//
//        createTransactionViewModel.createTransaction(transactionSendedObject)?.observe(this, Observer {
//            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
//        })
        viewDataBinding.btnSend.setOnClickListener{
            sendTransaction()
        }
    }

    fun sendTransaction(){
        var transactionSendedObject = TransactionSendedObject.Builder()
                .setFrom("0xca41a22df47207ff3b14398f7c98ef8cdbf649ca")
                .setTo(arguments!!.getString("address_target"))
                .setAmount(BalanceUltil.baseToSubunit(viewDataBinding.amount.text.toString(), 18))// 18 is Ether_decimals
                .setGasPrice(BigInteger.valueOf(1000000000L))
                .setGasLimit(BigInteger.valueOf(21000L))
                .build()
        createTransactionViewModel.createTransaction(transactionSendedObject)!!.observe(this, Observer {
            Toast.makeText(this.context,it,Toast.LENGTH_LONG).show()
        })
    }
}