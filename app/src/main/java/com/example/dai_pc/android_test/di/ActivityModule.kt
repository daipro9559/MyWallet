package com.example.dai_pc.android_test.di

import com.example.dai_pc.android_test.view.AddTokenActivity
import com.example.dai_pc.android_test.view.SplashActivity
import com.example.dai_pc.android_test.view.TransactionDetailActivity
import com.example.dai_pc.android_test.view.accounts.ManageAccountFragment
import com.example.dai_pc.android_test.view.wallet.ImportWalletActivity
import com.example.dai_pc.android_test.view.main.MainActivity
import com.example.dai_pc.android_test.view.rate.RateActivity
import com.example.dai_pc.android_test.view.setting.SettingActivity
import com.example.dai_pc.android_test.view.transaction.CreateTransactionActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by dai_pc on 6/15/2018.
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [SettingActivityModule::class])
    abstract fun settingActivity(): SettingActivity

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [CreateTransactionActivityModule::class])
    abstract fun createTransactionActivity(): CreateTransactionActivity

    @ContributesAndroidInjector(modules = [ImportActivityModule::class])
    abstract fun importActivity(): ImportWalletActivity

    @ContributesAndroidInjector
    abstract fun transactionDetailActivity(): TransactionDetailActivity

    @ContributesAndroidInjector
    abstract fun addTokenActivity(): AddTokenActivity

    @ContributesAndroidInjector
    abstract fun rateActivity(): RateActivity

}