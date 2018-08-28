package com.example.dai_pc.android_test.di

import com.example.dai_pc.android_test.view.transaction.SendTransactionFragment
import com.example.dai_pc.android_test.view.transaction.StellarSendTransactionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CreateTransactionActivityModule {

    @ContributesAndroidInjector(modules = [SendTransactionFragmentModule::class])
    abstract fun addAddressFragment():SendTransactionFragment

    @ContributesAndroidInjector()
    abstract fun stellarSendTransactionFragment():StellarSendTransactionFragment
}