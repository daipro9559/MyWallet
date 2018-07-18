package com.example.dai_pc.android_test.di

import com.example.dai_pc.android_test.view.main.address.MyAddressFragment
import com.example.dai_pc.android_test.view.main.rate.RateFragment
import com.example.dai_pc.android_test.view.main.transactions.ListTransactionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun fragmentHome(): ListTransactionFragment

    @ContributesAndroidInjector
    abstract fun fragmentMyAddress(): MyAddressFragment

    @ContributesAndroidInjector
    abstract fun rateFragment(): RateFragment
}