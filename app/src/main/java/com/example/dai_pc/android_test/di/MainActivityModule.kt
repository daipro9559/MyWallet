package com.example.dai_pc.android_test.di

import com.example.dai_pc.android_test.view.main.accounts.ManageAccountFragment
import com.example.dai_pc.android_test.view.main.address.MyAddressFragment
import com.example.dai_pc.android_test.view.main.token.TokenFragment
import com.example.dai_pc.android_test.view.main.transactions.ListTransactionFragment
import com.example.dai_pc.android_test.view.setting.SettingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [ListTransactionModule::class])
    abstract fun fragmentHome(): ListTransactionFragment

    @ContributesAndroidInjector
    abstract fun fragmentMyAddress(): MyAddressFragment

    @ContributesAndroidInjector
    abstract fun tokenFragment(): TokenFragment

    @ContributesAndroidInjector
    abstract fun manageAccountFragment(): ManageAccountFragment

    @ContributesAndroidInjector
    abstract fun settingFragment(): SettingFragment
}