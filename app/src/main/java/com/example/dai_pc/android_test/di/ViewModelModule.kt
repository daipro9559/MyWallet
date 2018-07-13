package com.example.dai_pc.android_test.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.dai_pc.android_test.base.MyViewModelFactory
import com.example.dai_pc.android_test.view.main.MainViewModel
import com.example.dai_pc.android_test.view.main.address.MyAddressViewModel
import com.example.dai_pc.android_test.view.transaction.CreateTransactionViewModel
import com.example.dai_pc.android_test.view.main.transactions.ListTransactionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * Created by dai_pc on 6/15/2018.
 */
@Module
abstract class ViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(CreateTransactionViewModel::class)
    abstract fun createTransactionViewModel(createTransactionViewModel: CreateTransactionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListTransactionViewModel::class)
    abstract fun listTransactionViewModel(createTransactionViewModel: ListTransactionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyAddressViewModel::class)
    abstract fun myAddresViewModel(myAddressViewModel: MyAddressViewModel): ViewModel

    @Binds
    abstract fun viewModelFactory (myViewModelFactory: MyViewModelFactory): ViewModelProvider.Factory
}