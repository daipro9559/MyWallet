package com.example.dai_pc.android_test.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject


abstract class BaseFragment<V:ViewDataBinding> :Fragment(){

    protected lateinit var viewDataBinding:V
    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(layoutInflater,getlayoutId(),container,false)
        return viewDataBinding.root
    }

    abstract fun getlayoutId():Int
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onStart() {
        super.onStart()


    }

    override fun onDestroyView() {
        super.onDestroyView()


    }


}