package com.example.dai_pc.android_test.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

class ItemViewHolder<V : ViewDataBinding>(var v : V) : RecyclerView.ViewHolder(v.root){

}