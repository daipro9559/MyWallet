package com.example.dai_pc.android_test.view.transaction

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentSendTransactionBinding
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.entity.TransactionSendObject
import com.example.dai_pc.android_test.ultil.BalanceUltil
import com.example.dai_pc.android_test.ultil.Ultil
import com.example.dai_pc.android_test.view.main.MainViewModel
import java.math.BigInteger

const val ADDRESS_TARGET = "address_target"

class SendTransactionFragment :BaseFragment<FragmentSendTransactionBinding>(){

    override fun getlayoutId() = R.layout.fragment_send_transaction

    lateinit var createTransactionViewModel: SendTransactionViewModel
    lateinit var mainViewModel: MainViewModel

    companion object {
        const val TAG = "Send transaction"
        fun newInstance(toAddress:String):SendTransactionFragment{
            val bundle = Bundle()
            bundle.putString(ADDRESS_TARGET,toAddress)
            val sendTransactionFragment  =  SendTransactionFragment()
            sendTransactionFragment.arguments = bundle
            return sendTransactionFragment
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.txtAddressTarget.text = arguments!!.getString(ADDRESS_TARGET)
        createTransactionViewModel = ViewModelProviders.of(this,viewModelFactory)[SendTransactionViewModel::class.java]
        mainViewModel  =  ViewModelProviders.of(this,viewModelFactory)[MainViewModel::class.java]

        mainViewModel.balanceLiveData.observe(this, Observer {

        })
        viewDataBinding.btnSend.setOnClickListener{
            sendTransaction()
        }
        createTransactionViewModel.sendResult.observe(this, Observer {
            viewDataBinding.resource = it
            viewDataBinding.loadingLayout.txtDescription.text = getString(R.string.sending_transaction)
            if (it!!.status == Resource.Status.SUCCESS) {
                Ultil.showDialogNotify(activity!!,getString(R.string.notify),getString(R.string.send_transaction_completed)){
                    activity!!.finish()
                }
            }else if (it!!.status == Resource.Status.ERROR){
                Ultil.showDialogNotify(activity!!,getString(R.string.error),it!!.messError!!){
                    activity!!.finish()
                }
            }
        })
    }

    fun sendTransaction(){
        var transactionSendedObject = TransactionSendObject.Builder()
                .setTo(arguments!!.getString(ADDRESS_TARGET))
                .setAmount(BalanceUltil.baseToSubunit(viewDataBinding.amount.text.toString(), 18))// 18 is Ether_decimals
                .setGasPrice(BigInteger.valueOf(1000000000L))
                .setGasLimit(BigInteger.valueOf(21000L))
                .build()
        createTransactionViewModel.createTransaction(transactionSendedObject)
    }
}