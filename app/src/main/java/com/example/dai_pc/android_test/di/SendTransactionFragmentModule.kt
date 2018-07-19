package com.example.dai_pc.android_test.di

import com.example.dai_pc.android_test.view.transaction.AddAddressReceiveFragment
import com.example.dai_pc.android_test.view.transaction.SendTransactionPresenter
import com.example.dai_pc.android_test.view.transaction.SendTransactionPresenterImp
import dagger.Binds
import dagger.Module

@Module
abstract class SendTransactionFragmentModule {

    @Binds
    abstract fun presenter(presenterImp: SendTransactionPresenterImp): SendTransactionPresenter<AddAddressReceiveFragment>
}