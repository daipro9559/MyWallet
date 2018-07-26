package com.example.dai_pc.android_test.view.main.token

import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseRecycleViewAdapter
import com.example.dai_pc.android_test.base.ItemViewHolder
import com.example.dai_pc.android_test.databinding.ItemTokenBinding
import com.example.dai_pc.android_test.entity.Token

class TokenAdapter :BaseRecycleViewAdapter<Token,ItemTokenBinding>(){
    override fun getlayoutId() = R.layout.item_token

    override fun bindData(i: Token, holder: ItemViewHolder<ItemTokenBinding>) {
        holder.v.token  = i
    }
}