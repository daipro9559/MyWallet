package com.example.dai_pc.android_test.di

import android.arch.lifecycle.ViewModelProvider
import com.example.dai_pc.android_test.base.MyViewModelFactory
import dagger.Binds
import dagger.Module


/**
 * Created by dai_pc on 6/15/2018.
 */
@Module
abstract class ViewModelModule{

    @Binds
    abstract fun viewModelBase (myViewModelFactory: MyViewModelFactory):ViewModelProvider.Factory;
}