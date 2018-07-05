package com.example.dai_pc.android_test.di

import com.example.dai_pc.android_test.view.transaction.AddAddressReceiveFragment
import com.example.dai_pc.android_test.view.transaction.SendTransactionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CreateTransactionActivityModule {
    @ContributesAndroidInjector
    abstract fun addAddressFragment():AddAddressReceiveFragment
    @ContributesAndroidInjector
    abstract fun sendTransactionFragment():SendTransactionFragment
}