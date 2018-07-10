package com.example.dai_pc.android_test.di

import com.example.dai_pc.android_test.view.account.ListAccountFragment
import com.example.dai_pc.android_test.view.main.address.MyAddressFragment
import com.example.dai_pc.android_test.view.main.transactions.ListTransactionFragment
import com.example.dai_pc.android_test.view.network.NetworkFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun fragmentList(): ListAccountFragment

    @ContributesAndroidInjector
    abstract fun fragmentHome(): ListTransactionFragment

    @ContributesAndroidInjector
    abstract fun fragmentNetwork(): NetworkFragment

    @ContributesAndroidInjector
    abstract fun fragmentMyAddress(): MyAddressFragment
}