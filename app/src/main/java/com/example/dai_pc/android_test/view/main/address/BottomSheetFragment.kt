package com.example.dai_pc.android_test.view.main.address

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dai_pc.android_test.R
import kotlinx.android.synthetic.main.dialog_add_wallet.*
const val TAG ="BottomSheetTag"
class BottomSheetFragment :BottomSheetDialogFragment(){

    lateinit var callback : (View)-> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_add_wallet,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        create_wallet.setOnClickListener {
            callback(it)
        }
        import_wallet.setOnClickListener{
            callback(it)
        }
    }
}