package com.example.dai_pc.android_test.view.account

import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseRecycleViewAdapter
import com.example.dai_pc.android_test.base.ItemViewHolder
import com.example.dai_pc.android_test.databinding.ItemAccountBinding
import org.ethereum.geth.Account

class AccountRecycleViewAdapter : BaseRecycleViewAdapter<Account,ItemAccountBinding>() {
    override fun getlayoutId(): Int = R.layout.item_account

    override fun bindData(i: Account, holder: ItemViewHolder<ItemAccountBinding>) {
        holder.v.txtAddress.text = i.address.hex.toLowerCase()
    }
}