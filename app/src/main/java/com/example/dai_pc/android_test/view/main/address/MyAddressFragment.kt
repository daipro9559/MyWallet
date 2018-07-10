package com.example.dai_pc.android_test.view.main.address

import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.widget.Toast
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.databinding.FragmentMyAddressBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

const val QR_IMAGE_WIDTH_RATIO = 0.9

class MyAddressFragment : BaseFragment<FragmentMyAddressBinding>() {

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
        async(CommonPool) {
            val bitmap = genAddressToBarCode("0x6480600bad47cB4D2d1E827592e199886Fd5fb3a")
            async (UI){
                viewDataBinding.imgBarcode.setImageBitmap(bitmap)
                viewDataBinding.txtAddress.text  = "0x6480600bad47cB4D2d1E827592e199886Fd5fb3a"
            }
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
}