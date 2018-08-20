package com.example.dai_pc.android_test.view.main.transactions

import android.annotation.SuppressLint
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseRecycleViewAdapter
import com.example.dai_pc.android_test.base.ItemViewHolder
import com.example.dai_pc.android_test.databinding.ItemTransactionBinding
import com.example.dai_pc.android_test.entity.Transaction
import com.example.dai_pc.android_test.ultil.Ultil
import java.math.BigDecimal

class TransactionAdapter(val Click:(Transaction)->Unit) :BaseRecycleViewAdapter<Transaction,ItemTransactionBinding>(){

    lateinit var myWallet:String
    override fun getlayoutId() = R.layout.item_transaction


    override fun bindData(i: Transaction, holder: ItemViewHolder<ItemTransactionBinding>) {
        holder.v.transaction = i

        holder.v.time.text =Ultil.getTimeFromTimeStamp(i.timeStamp!!.toLong())
        if (i.from.endsWith(myWallet,true))  {
            holder.v.value.text ="-" + BigDecimal(i.value.toBigIntegerOrNull(),18).toFloat().toString() + " ETH"
            holder.v.txtAddress.text = i.to
            holder.v.txtFromTo.text = "To: "
            holder.v.value.setBackgroundResource(R.color.colorRedSent)
        }else {
            holder.v.value.text ="+" + BigDecimal(i.value.toBigIntegerOrNull(),18).toFloat().toString() + " ETH"
            holder.v.txtFromTo.text = "From: "
            holder.v.txtAddress.text = i.from
            holder.v.value.setBackgroundResource(R.color.colorGreenReceived)
        }
        holder.v.viewRoot.setOnClickListener {
            Click(i)
        }
    }
}