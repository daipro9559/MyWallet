package com.example.dai_pc.android_test.view.main.token

import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseRecycleViewAdapter
import com.example.dai_pc.android_test.base.ItemViewHolder
import com.example.dai_pc.android_test.databinding.ItemTokenBinding
import com.example.dai_pc.android_test.entity.BalanceToken
import com.example.dai_pc.android_test.entity.Token
import java.math.BigDecimal

class TokenAdapter(val CallBack:(Token,Int) ->Unit, val itemClick:(Token) ->Unit, val itemLongClick:(Token)->Unit) :BaseRecycleViewAdapter<Token,ItemTokenBinding>(){
    private val listPositionReceived = ArrayList<Int>()
    override fun getlayoutId() = R.layout.item_token
    override fun bindData(i: Token, holder: ItemViewHolder<ItemTokenBinding>) {
        if (!listPositionReceived.contains(holder.adapterPosition)){
            CallBack(i,holder.adapterPosition)
            listPositionReceived.add(holder.adapterPosition)
        }
        holder.v.constrainLayout.setOnClickListener{
            itemClick(i)
        }
        holder.v.constrainLayout.setOnLongClickListener {
            itemLongClick(i)
            true
        }
        holder.v.token  = i

    }

    override fun swapListItem(items: List<Token>) {
        listPositionReceived.clear()
        super.swapListItem(items)

    }

    fun updateBalance(balanceToken: BalanceToken){
        items[balanceToken.position].balance = BigDecimal(balanceToken.value.toBigIntegerExact(),18).toFloat().toString()
        notifyItemChanged(balanceToken.position)
    }

}