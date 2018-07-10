package com.example.dai_pc.android_test.view.main.transactions

import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseRecycleViewAdapter
import com.example.dai_pc.android_test.base.ItemViewHolder
import com.example.dai_pc.android_test.databinding.ItemTransactionBinding
import com.example.dai_pc.android_test.entity.Transaction
import com.example.dai_pc.android_test.ultil.Ultil
import java.math.BigDecimal

class TransactionAdapter :BaseRecycleViewAdapter<Transaction,ItemTransactionBinding>(){
    lateinit var addressMain:String
    override fun getlayoutId() = R.layout.item_transaction

    override fun bindData(i: Transaction, holder: ItemViewHolder<ItemTransactionBinding>) {
        holder.v.transaction = i
        holder.v.value.text =BigDecimal(i.value,18).toFloat().toString()
        holder.v.time.text =Ultil.getTimeFromTimeStamp(i.timeStamp!!.toLong())
        if (i.from.endsWith(addressMain,true))  {
            holder.v.txtAddress.text = i.to
            holder.v.txtFromTo.text = "To: "
            holder.v.icon.setImageDrawable(holder.v.root.context.getDrawable(R.drawable.ic_forward))
        }else
        {
            holder.v.txtFromTo.text = "From: "
            holder.v.txtAddress.text = i.from
            holder.v.icon.setImageDrawable(holder.v.root.context.getDrawable(R.drawable.ic_back))

        }
    }
}