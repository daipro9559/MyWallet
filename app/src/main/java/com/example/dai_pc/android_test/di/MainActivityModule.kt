package com.example.dai_pc.android_test.di

import com.example.dai_pc.android_test.view.main.address.MyAddressFragment
import com.example.dai_pc.android_test.view.rate.RateActivity
import com.example.dai_pc.android_test.view.main.token.TokenFragment
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
    abstract fun tokenFragment(): TokenFragment
}