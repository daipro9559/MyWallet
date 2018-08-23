package com.example.dai_pc.android_test.view.transaction

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
import com.example.dai_pc.android_test.view.main.token.TokenViewModel
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
        fun newInstance(isSendToken: Boolean, symbol: String, contractAddress: String): SendTransactionFragment {
            val bundle = Bundle()
            bundle.putBoolean(Constant.IS_SEND_TOKEN, isSendToken)
            bundle.putString(Constant.SYMBOL_TOKEN, symbol)
            bundle.putString(Constant.CONTRACT_ADDRESS, contractAddress)
            val addAddressReceiveFragment = SendTransactionFragment()
            addAddressReceiveFragment.arguments = bundle
            return addAddressReceiveFragment
        }

        fun newInstance(): SendTransactionFragment {
            val bundle = Bundle()
            val addAddressReceiveFragment = SendTransactionFragment()
            addAddressReceiveFragment.arguments = bundle
            return addAddressReceiveFragment
        }
    }

    lateinit var createTransactionViewModel: SendTransactionViewModel
    @Inject
    lateinit var presenter: SendTransactionPresenter<SendTransactionFragment>
    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private val GAS_PRICE_DEFAULT = 21000000000L
    private val GAS_LIMIT_DEFAULT = 90000L
    private val GAS_LIMIT_DEFAULT_TOKEN = 144000L

    private var isSendToken = false

    private var symbolToken: String? = null
    private var contractAddress: String? = null

    override fun getlayoutId() = R.layout.fragment_add_address_receive

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isSendToken = arguments!!.getBoolean(Constant.IS_SEND_TOKEN)
        symbolToken = arguments!!.getString(Constant.SYMBOL_TOKEN)
        contractAddress = arguments!!.getString(Constant.CONTRACT_ADDRESS)
    }

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


        inintView()
    }

    fun validateTransaction(password: String, isRequirePass: Boolean) {
        if (TextUtils.isEmpty(viewDataBinding.amount.text.toString())) {
            viewDataBinding.amount.error = getString(R.string.field_require)
            return
        }
        if (TextUtils.isEmpty(viewDataBinding.txtAddress.text.toString())) {
            viewDataBinding.txtAddress.error = getString(R.string.field_require)
            return
        }
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

    override fun sendTransaction(transactionSendObject: TransactionSendObject) {
        if (isSendToken) {
            createTransactionViewModel.sendToken(transactionSendObject, contractAddress!!)
            return
        }
        createTransactionViewModel.createTransaction(transactionSendObject)
    }

    override fun errorInput(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun toogleExpanView() {
        if (expandView.isExpanded) {
            viewDataBinding.iconRotate.startAnimation(AnimationUtils.loadAnimation(activity!!.applicationContext, R.anim.rotate_up))
            viewDataBinding.txtOption.text = getString(R.string.show_option)

        } else {
            viewDataBinding.iconRotate.startAnimation(AnimationUtils.loadAnimation(activity!!.applicationContext, R.anim.rotate_down))
            viewDataBinding.txtOption.text = getString(R.string.hide_option)

        }
        expandView.toggle(true)
    }

    fun inintView() {
        if (isSendToken) {
            gasLimit.setText(GAS_LIMIT_DEFAULT_TOKEN.toString())
        } else {
            gasLimit.setText(GAS_LIMIT_DEFAULT.toString())
        }
        gasPrice.setText(GAS_PRICE_DEFAULT.toString())
        btnSend.setOnClickListener {
            if (preferenceHelper.getBoolean(Constant.KEY_REQUIRE_PASSWORD)) {
                Ultil.showDialogInputPassword(activity!!, getString(R.string.input_pass_account), getString(R.string.Send)) {
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
            if (isSendToken) {
                gasLimit.setText(GAS_LIMIT_DEFAULT_TOKEN.toString())

            } else {
                gasLimit.setText(GAS_LIMIT_DEFAULT.toString())
            }
        }
        gasPriceReset.setOnClickListener {
            gasPrice.setText(GAS_PRICE_DEFAULT.toString())
        }
        if (isSendToken) {
            contractAddress?.let {
                createTransactionViewModel.getBalanceToken(contractAddress!!)
                createTransactionViewModel.balanceToken.observe(this, Observer {
                    viewDataBinding.balance.text = BigDecimal(it!!.toBigIntegerExact(), 18).toFloat().toString() + " $symbolToken"
                })

                viewDataBinding.layoutAmount.hint = getString(R.string.amount, symbolToken)
            }


        } else {
            viewDataBinding.layoutAmount.hint = getString(R.string.amount, "ETH")
//            mainViewModel.balanceLiveData.observe(this, Observer {
//                it!!.t?.let {
//                    val data = BigDecimal(it, 18)
//                    viewDataBinding.balance.text = data.toFloat().toString() + "ETH"
//                }
//            })
        }

    }
}