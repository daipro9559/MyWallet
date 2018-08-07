package com.example.dai_pc.android_test.view.main.address

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentMyAddressBinding
import com.example.dai_pc.android_test.repository.WalletRepository
import com.example.dai_pc.android_test.ultil.Callback
import com.example.dai_pc.android_test.view.accounts.ManageAccountActivity
import com.example.dai_pc.android_test.view.wallet.ImportWalletActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.fragment_my_address.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

const val QR_IMAGE_WIDTH_RATIO = 0.9
const val KEY_ADDRESS = "key_address"
const val SHARE_REQUEST_CODE = 131

class MyAddressFragment : BaseFragment<FragmentMyAddressBinding>() {
    @Inject
    lateinit var walletRepository: WalletRepository
    private lateinit var myAddressViewModel: MyAddressViewModel

    lateinit var callback: Callback<String>

    companion object {
        fun newInstance(): MyAddressFragment {
            var bundle = Bundle()
            var homeFragment = MyAddressFragment()
            homeFragment.arguments = bundle
            return homeFragment
        }
    }

    override fun getlayoutId() = R.layout.fragment_my_address

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        myAddressViewModel = ViewModelProviders.of(this, viewModelFactory)[MyAddressViewModel::class.java]
        refresh()
        floatButton_add.setOnClickListener { clickAddAddress() }

        btn_copy.setOnClickListener {
            val clipboard = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(KEY_ADDRESS, walletRepository.accountSelected.value)
            if (clipboard != null) {
                clipboard.primaryClip = clip
            }
            Toast.makeText(context!!, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }
        btn_export.setOnClickListener {
            createDialogExport()
        }
        btn_manage.setOnClickListener {
            startActivity(Intent(activity!!,ManageAccountActivity::class.java))
        }
    }

    fun genAddressToBarCode(address: String): Bitmap? {
        val size = Point()
        activity!!.windowManager.defaultDisplay.getSize(size)
        val imageSize = (size.x * QR_IMAGE_WIDTH_RATIO).toInt()
        try {
            val bitMatrix = MultiFormatWriter().encode(
                    address,
                    BarcodeFormat.QR_CODE,
                    imageSize,
                    imageSize, null)
            val barcodeEncoder = BarcodeEncoder()
            return barcodeEncoder.createBitmap(bitMatrix)
        } catch (e: Exception) {
            Toast.makeText(activity!!.applicationContext, "gen  code fail", Toast.LENGTH_SHORT)
                    .show()
        }

        return null
    }

    private fun clickAddAddress() {
        val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.callback = {
            if (it.id == R.id.create_wallet) {
                fragmentManager!!.beginTransaction().remove(fragmentManager!!.findFragmentByTag("bottom_sheet")).commit()
                buildDialogCreateWallet()
            } else {
                fragmentManager!!.beginTransaction().remove(fragmentManager!!.findFragmentByTag("bottom_sheet")).commit()
                startActivity(Intent(activity!!,ImportWalletActivity::class.java))
            }
        }
        fragmentManager!!.beginTransaction().add(bottomSheetFragment, "bottom_sheet").disallowAddToBackStack().commit()
    }

    private fun buildDialogCreateWallet() {
        var alertDialog: AlertDialog? = null
        val builder = AlertDialog.Builder(activity!!)
                .setTitle(R.string.create_account)
                .setView(R.layout.dialog_create_wallet)
                .setNegativeButton(R.string.cancel) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    dialogInterface.dismiss()
                }
                .setPositiveButton(R.string.create) { _, i ->
                    val editInput: TextInputEditText = alertDialog!!.findViewById<TextInputEditText>(R.id.edt_pass)!!
                    if (editInput.text.isEmpty()) {
                        editInput.error = context!!.getString(R.string.no_input_password)
                    } else {
                        myAddressViewModel.createAccount(editInput.text.toString())
                        myAddressViewModel.liveDataAccount.observe(this, Observer {
                            it?.let {
                                val account = it
                                Toast.makeText(context!!.applicationContext, context!!.getString(R.string.create_completed), Toast.LENGTH_LONG).show()
                                // if no have account , set account was have
                                walletRepository.saveAccountSelect(it!!.address.hex.toString())
                                callback?.let {
                                    callback(account!!.address.hex.toString())
                                }
                            }
                        })
                    }
                }
        alertDialog = builder.create()
        alertDialog.show()
    }

    fun refresh() {
        if (walletRepository.accountSelected.value == null) {
            btn_copy.visibility = View.GONE
            btn_export.visibility = View.GONE
        } else {
            btn_copy.visibility = View.VISIBLE
            btn_export.visibility = View.VISIBLE
        }
        walletRepository.accountSelected.value?.let {
            async(CommonPool) {
                val bitmap = genAddressToBarCode(walletRepository.accountSelected.value!!)
                async(UI) {
                    viewDataBinding.imgBarcode.setImageBitmap(bitmap)
                    viewDataBinding.txtAddress.text = walletRepository.accountSelected.value!!
                }
            }
        }

    }

    private fun createDialogExport() {
        val view = layoutInflater.inflate(R.layout.dialog_create_wallet,null)
        val textInputEditText = view.findViewById<TextInputEditText>(R.id.edt_pass)
        val builder = AlertDialog.Builder(activity!!)
                .setTitle(R.string.export_account)
                .setView(view)
                .setNegativeButton(R.string.cancel) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    dialogInterface.dismiss()
                }
                .setPositiveButton(R.string.export) { _, i ->
                    myAddressViewModel.export(textInputEditText.text.toString().trim())
                    myAddressViewModel.liveDataExport.observe(this, Observer {
                        openShareDialog(it!!)
                    })
                }
        builder.create().show()
    }

    private fun openShareDialog(jsonData: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Keystore")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, jsonData)
        startActivityForResult(
                Intent.createChooser(sharingIntent, "Share via"),
                SHARE_REQUEST_CODE)
    }
}