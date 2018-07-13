package com.example.dai_pc.android_test.view.main.address

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentMyAddressBinding
import com.example.dai_pc.android_test.repository.WalletRepository
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.fragment_add_address_receive.*
import kotlinx.android.synthetic.main.fragment_my_address.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

const val QR_IMAGE_WIDTH_RATIO = 0.9
const val KEY_ADDRESS = "key_address"


class MyAddressFragment : BaseFragment<FragmentMyAddressBinding>() {
    @Inject
    lateinit var walletRepository: WalletRepository
    lateinit var dialog :BottomSheetDialog

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

    fun clickAddAddress() {
        val viewDialog = activity!!.layoutInflater.inflate(R.layout.dialog_add_wallet, null)
        dialog = BottomSheetDialog(context!!,R.style.BottomDialog)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(viewDialog)
        val createWallet = dialog.findViewById<LinearLayoutCompat>(R.id.create_wallet)
        createWallet!!.setOnClickListener {
            buildDialogCreateWallet()
        }
        val importWallet = dialog.findViewById<LinearLayoutCompat>(R.id.create_wallet)
        importWallet!!.setOnClickListener {
        }

        dialog.show()
    }

    fun buildDialogCreateWallet() {
        val view = LayoutInflater.from(context!!).inflate(R.layout.dialog_create_wallet, null, false)
        val builder = AlertDialog.Builder(activity!!, R.style.DialogApp)
                .setTitle(R.string.create_wallet)
                .setView(view)
                .setPositiveButton(R.string.cancel) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    dialogInterface.dismiss()
                }
                .setNegativeButton(R.string.create) { dialogInterface, i ->
                    val editInput: TextInputEditText = view.findViewById(R.id.edt_pass)
                    if (editInput.text.isEmpty()) {
                        editInput.error = context!!.getString(R.string.no_input_password)
                    } else {
                    }
                }
        builder.create().show()
    }

    fun refresh() {
        async(CommonPool) {
            val bitmap = genAddressToBarCode(walletRepository.accountSelected.value!!)
            async(UI) {
                viewDataBinding.imgBarcode.setImageBitmap(bitmap)
                viewDataBinding.txtAddress.text = walletRepository.accountSelected.value!!
            }
        }
    }
}