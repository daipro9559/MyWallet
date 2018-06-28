package com.example.dai_pc.android_test.view.network

import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseRecycleViewAdapter
import com.example.dai_pc.android_test.base.ItemViewHolder
import com.example.dai_pc.android_test.databinding.ItemAccountBinding
import com.example.dai_pc.android_test.entity.NetworkProvider

class NetworkRecycleViewAdapter
(
        val clickCallback : ((NetworkProvider) -> Unit)
): BaseRecycleViewAdapter<NetworkProvider,ItemAccountBinding>(){
    override fun getlayoutId(): Int = R.layout.item_account

    override fun bindData(i: NetworkProvider, holder: ItemViewHolder<ItemAccountBinding>) {
        holder.v.txtAddress.text = i.name
        holder.v.root.setOnClickListener { clickCallback.invoke(i) }
    }
}