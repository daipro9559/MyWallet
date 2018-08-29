package com.example.dai_pc.android_test.view.main.transactions

import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseRecycleViewAdapter
import com.example.dai_pc.android_test.base.ItemViewHolder
import com.example.dai_pc.android_test.databinding.ItemTransactionBinding
import com.example.stellar.responses.TransactionResponse
import java.text.SimpleDateFormat
import java.util.*

class StellarTransactionAdapter : BaseRecycleViewAdapter<TransactionResponse, ItemTransactionBinding>() {
    lateinit var myAccount: String
    override fun bindData(i: TransactionResponse, holder: ItemViewHolder<ItemTransactionBinding>) {
        holder.v.value.text = i.feePaid.toString()
        holder.v.time.text = handleTime(i.createdAt)
        holder.v.txtAddress.text = i.sourceAccount.accountId
    }

    override fun getlayoutId() = R.layout.item_transaction

    private fun handleTime(time:String) :String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val date = formatter.parse(time)
        val currentTimeZone = Calendar.getInstance().timeZone
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy hh:mm")
        dateFormatter.timeZone = currentTimeZone
        return dateFormatter.format(date)
    }
}