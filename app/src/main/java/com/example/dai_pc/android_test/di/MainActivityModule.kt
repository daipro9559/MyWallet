package com.example.dai_pc.android_test.di

import com.example.dai_pc.android_test.service.AccountService
import com.example.dai_pc.android_test.service.AccountServiceImp
import com.example.dai_pc.android_test.view.account.ListAccountFragment
import com.example.dai_pc.android_test.view.home.HomeFragment
import com.example.dai_pc.android_test.view.network.NetworkFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun fragmentList(): ListAccountFragment

    @ContributesAndroidInjector
    abstract fun fragmentHome(): HomeFragment

    @ContributesAndroidInjector
    abstract fun fragmentNetwork(): NetworkFragment

    @Binds
    abstract fun createServiceAccount(accountService: AccountServiceImp): AccountService
}