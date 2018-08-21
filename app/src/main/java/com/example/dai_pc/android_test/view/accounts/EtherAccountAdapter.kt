package com.example.dai_pc.android_test.view.accounts

import android.support.v7.widget.PopupMenu
import android.view.Gravity
import android.view.MenuInflater
import android.view.View
import com.bumptech.glide.Glide
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseRecycleViewAdapter
import com.example.dai_pc.android_test.base.ItemViewHolder
import com.example.dai_pc.android_test.customview.Identicon
import com.example.dai_pc.android_test.databinding.ItemWalletBinding
import org.ethereum.geth.Account

class EtherAccountAdapter (val menuClick: MenuClick) : BaseRecycleViewAdapter<Account, ItemWalletBinding>() {
    override fun getlayoutId() = R.layout.item_wallet
    override fun bindData(i: Account, holder: ItemViewHolder<ItemWalletBinding>) {
        holder.v.txtWallet.text = i.address.hex.toString()
        holder.v.iconDropDown.setOnClickListener {
            showPopupMenu(it, holder.adapterPosition)
        }
       Glide.with(holder.v.root)
               .load(Identicon.create(holder.adapterPosition.toString()))
               .into(holder.v.iconAccount)
    }

    private fun showPopupMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(view.context, view)
        MenuInflater(view.context).inflate(R.menu.menu_manage_wallet, popupMenu.menu)
        popupMenu.gravity = Gravity.BOTTOM
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener {
            menuClick(it.itemId,items[position].address.hex.toString())
            true
        }
    }

}