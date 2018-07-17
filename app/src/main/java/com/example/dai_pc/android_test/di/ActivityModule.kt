package com.example.dai_pc.android_test.di

import com.example.dai_pc.android_test.service.AccountService
import com.example.dai_pc.android_test.service.AccountServiceImp
import com.example.dai_pc.android_test.view.SplashActivity
import com.example.dai_pc.android_test.view.import.ImportWalletActivity
import com.example.dai_pc.android_test.view.main.MainActivity
import com.example.dai_pc.android_test.view.setting.SettingActivity
import com.example.dai_pc.android_test.view.transaction.CreateTransactionActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

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

    @ContributesAndroidInjector
    abstract fun importWalletActivity(): ImportWalletActivity

}