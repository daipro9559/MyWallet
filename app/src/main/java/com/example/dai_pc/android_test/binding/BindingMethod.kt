package com.example.dai_pc.android_test.binding

import android.databinding.BindingAdapter
import android.view.View

object BindingMethod {
    @JvmStatic
    @BindingAdapter("visisable")
    fun showLoading(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}