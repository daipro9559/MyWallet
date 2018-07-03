package com.example.dai_pc.android_test.base

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<V : ViewDataBinding> :AppCompatActivity(),HasSupportFragmentInjector {

    lateinit var viewDataBinding: V
    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory
    @Inject
    lateinit var dispatchingFragment : DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this,getLayoutId())


    }

    override fun onStart() {0
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingFragment
    }
    abstract fun  getLayoutId():Int
}