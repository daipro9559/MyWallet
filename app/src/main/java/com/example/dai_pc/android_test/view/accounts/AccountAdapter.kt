package com.example.dai_pc.android_test.view.accounts

import android.support.v7.widget.PopupMenu
import android.view.Gravity
import android.view.MenuInflater
import android.view.View
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseRecycleViewAdapter
import com.example.dai_pc.android_test.base.ItemViewHolder
import com.example.dai_pc.android_test.databinding.ItemAccountBinding
import com.example.dai_pc.android_test.databinding.ItemWalletBinding
import org.ethereum.geth.Account
import java.text.FieldPosition

typealias MenuClick = (menuId: Int, address: String) -> Unit

class AccountAdapter(val menuClick: MenuClick) : BaseRecycleViewAdapter<Account, ItemWalletBinding>() {
    override fun getlayoutId() = R.layout.item_wallet
    override fun bindData(i: Account, holder: ItemViewHolder<ItemWalletBinding>) {
        holder.v.txtWallet.text = i.address.hex.toString()
        holder.v.iconDropDown.setOnClickListener {
            showPopupMenu(it, holder.adapterPosition)
        }
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
