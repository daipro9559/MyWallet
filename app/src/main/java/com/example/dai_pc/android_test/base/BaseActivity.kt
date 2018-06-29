package com.example.dai_pc.android_test.base

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
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

abstract class BaseActivity<V : ViewDataBinding> :AppCompatActivity(),HasSupportFragmentInjector, LifecycleOwner {

    lateinit var viewDataBinding: V
    protected val mLifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)


    @Inject
    lateinit var dispatchingFragment : DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this,getLayoutId())
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)


    }

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)

    }

    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED)

    }
    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingFragment
    }
    abstract fun  getLayoutId():Int
}