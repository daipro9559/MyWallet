package com.example.dai_pc.android_test.view.main

import android.content.Context
import android.content.SharedPreferences
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.example.dai_pc.android_test.R
import com.example.dai_pc.android_test.base.BaseFragment
import com.example.dai_pc.android_test.base.Constant

abstract class MainFragment<V : ViewDataBinding> :BaseFragment<V>(),SharedPreferences.OnSharedPreferenceChangeListener{
    private lateinit var sharedPreferences: SharedPreferences
    private var isNeedReload = false
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferences = activity!!.getSharedPreferences(getString(R.string.app_name),Context.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        if (isNeedReload){
            refresh()
            isNeedReload = false
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when (p1) {
            Constant.ACCOUNT_ETHEREUM_KEY -> {
                isNeedReload = true
                changeAccount()
            }
            Constant.ACCOUNT_STELLAR_KEY -> {
                isNeedReload = true
                changeAccount()
            }
            Constant.KEY_NETWORK_STELLAR -> {
                isNeedReload = true
                changeNetwork()
            }
            Constant.KEY_NETWORK_ETHER-> {
                isNeedReload = true
                changeNetwork()
            }
            Constant.PLATFORM_KEY ->{
                isNeedReload = true
                changePlatform()
            }
        }
    }

    abstract fun refresh()
    abstract fun changeNetwork()
    abstract fun changeAccount()
    abstract fun changePlatform()


}