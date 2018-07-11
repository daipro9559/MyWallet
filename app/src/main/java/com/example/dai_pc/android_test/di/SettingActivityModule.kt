package com.example.dai_pc.android_test.di

import com.example.dai_pc.android_test.view.setting.SettingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingActivityModule {
    @ContributesAndroidInjector
    abstract fun settingFragment():SettingFragment


}