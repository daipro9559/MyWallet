package com.example.dai_pc.android_test.base

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.dai_pc.android_test.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity(), HasSupportFragmentInjector {

    lateinit var viewDataBinding: V
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var dispatchingFragment: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())

    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingFragment
    }

    abstract fun getLayoutId(): Int

    fun startActivity(destinationActivity: Class<Any>) {
        startActivity(Intent(this, destinationActivity))
    }

    fun startActivityAndFinish(destinationActivity: Class<Any>) {
        startActivity(destinationActivity)
        finish()
    }

    fun enableHomeHomeAsUp() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun hideHomeHomeAsUp() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}