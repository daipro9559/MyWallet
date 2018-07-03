package com.example.dai_pc.android_test.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseRecycleViewAdapter<I, V : ViewDataBinding> : RecyclerView.Adapter<ItemViewHolder<V>>() {
    var items: List<I> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<V> {
        return ItemViewHolder(DataBindingUtil.inflate<V>(LayoutInflater.from(parent.context), getlayoutId(), parent, false))
    }

    abstract fun getlayoutId(): Int

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder<V>, position: Int) {
        bindData(items[position], holder)
        holder.v.executePendingBindings()
    }

    abstract fun bindData(i: I, holder: ItemViewHolder<V>)

    fun swapListItem(items: List<I>) {
        this.items = items
        notifyDataSetChanged()
    }

}