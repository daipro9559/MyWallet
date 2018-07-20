package com.example.dai_pc.android_test.view.transaction

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.databinding.FragmentAddAddressReceiveBinding
import com.example.dai_pc.android_test.entity.Resource
import com.example.dai_pc.android_test.entity.TransactionSendObject
import com.example.dai_pc.android_test.ultil.BalanceUltil
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import com.example.dai_pc.android_test.ultil.Ultil
import com.example.dai_pc.android_test.view.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_address_receive.*
import org.walleth.activities.qrscan.QRScanActivity
import org.walleth.activities.qrscan.SCAN_REQUEST_CODE
import org.walleth.activities.qrscan.SCAN_RESULT
import java.math.BigDecimal
import java.math.BigInteger
import javax.inject.Inject

class SendTransactionFragment : BaseFragment<FragmentAddAddressReceiveBinding>(), SendTransactionView {

    companion object {
        const val TAG = "Send transaction"
        fun newInstance(): SendTransactionFragment {
            val bundle = Bundle()
            val addAddressReceiveFragment = SendTransactionFragment()
            addAddressReceiveFragment.arguments = bundle
            return addAddressReceiveFragment
        }
    }

    lateinit var createTransactionViewModel: SendTransactionViewModel
    lateinit var mainViewModel: MainViewModel
    @Inject
    lateinit var presenter: SendTransactionPresenter<SendTransactionFragment>
    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val GAS_PRICE_DEFAULT = 1000000000L
    private val GAS_LIMIT_DEFAULT = 21000L

    override fun getlayoutId() = R.layout.fragment_add_address_receive


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.bindView(this)
        btnQRCode.setOnClickListener {
            startActivityForResult(Intent(activity, QRScanActivity::class.java), SCAN_REQUEST_CODE)
        }
        createTransactionViewModel = ViewModelProviders.of(this, viewModelFactory)[SendTransactionViewModel::class.java]

        createTransactionViewModel.sendResult.observe(this, Observer {
            viewDataBinding.resource = it
            viewDataBinding.loadingLayout.txtDescription.text = getString(R.string.sending_transaction)
            if (it!!.status == Resource.Status.SUCCESS) {
                Ultil.showDialogNotify(activity!!, getString(R.string.notify), getString(R.string.send_transaction_completed)) {
                    activity!!.finish()
                }
            } else if (it!!.status == Resource.Status.ERROR) {
                Ultil.showDialogNotify(activity!!, getString(R.string.error), it!!.messError!!) {
                    activity!!.finish()
                }
            }
        })

        mainViewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        mainViewModel.balanceLiveData.observe(this, Observer {
            it!!.t?.let {
                val data = BigDecimal(it, 18)
                viewDataBinding.balance.text = data.toFloat().toString() + " ETH"
            }
        })
        mainViewModel.fetchBalance()
        inintView()
    }

    fun validateTransaction(password: String, isRequirePass: Boolean) {
        var transactionSendedObject = TransactionSendObject.Builder()
                .setTo(viewDataBinding.txtAddress.text.toString())
                .setAmount(BalanceUltil.baseToSubunit(viewDataBinding.amount.text.toString(), 18))// 18 is Ether_decimals
                .setGasPrice(BigInteger.valueOf(gasPrice.text.toString().toLong()))
                .setGasLimit(BigInteger.valueOf(gasLimit.text.toString().toLong()))
                .setPass(password)
                .build()
        presenter.validateData(transactionSendedObject, isRequirePass)
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

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
    }

    override fun sendTransaction(transactionSendObject: TransactionSendObject) = createTransactionViewModel.createTransaction(transactionSendObject)


    override fun errorInput(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun toogleExpanView() {
        if (expandView.isExpanded){
            viewDataBinding.iconRotate.startAnimation(AnimationUtils.loadAnimation(activity!!.applicationContext,R.anim.rotate_up))
            viewDataBinding.txtOption.text = getString(R.string.show_option)

        }else{
            viewDataBinding.iconRotate.startAnimation(AnimationUtils.loadAnimation(activity!!.applicationContext,R.anim.rotate_down))
            viewDataBinding.txtOption.text = getString(R.string.hide_option)

        }
        expandView.toggle(true)
    }
    fun inintView(){
        gasLimit.setText( GAS_LIMIT_DEFAULT.toString())
        gasPrice.setText(GAS_PRICE_DEFAULT.toString())
        btnSend.setOnClickListener {
            if (preferenceHelper.getBoolean(Constant.KEY_REQUIRE_PASSWORD)) {
                Ultil.showDialogInputPassword(activity!!, getString(R.string.input_pass_wallet), getString(R.string.Send)) {
                    validateTransaction(it, true)
                }
            } else {
                validateTransaction("", false)
            }
        }
        cardView.setOnClickListener {
            toogleExpanView()
        }
        gasLimitReset.setOnClickListener {
            gasLimit.setText( GAS_LIMIT_DEFAULT.toString())
        }
        gasPriceReset.setOnClickListener {
            gasPrice.setText(GAS_PRICE_DEFAULT.toString())
        }
    }
}