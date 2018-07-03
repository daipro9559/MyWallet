package com.example.dai_pc.android_test.view.transactions

import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseRecycleViewAdapter
import com.example.dai_pc.android_test.base.ItemViewHolder
import com.example.dai_pc.android_test.databinding.ItemTransactionBinding
import com.example.dai_pc.android_test.entity.Transaction

class TransactionAdapter :BaseRecycleViewAdapter<Transaction,ItemTransactionBinding>(){
    override fun getlayoutId() = R.layout.item_transaction

    override fun bindData(i: Transaction, holder: ItemViewHolder<ItemTransactionBinding>) {
        holder.v.transaction = i
    }
}