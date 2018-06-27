package com.example.dai_pc.android_test.di

import com.example.dai_pc.android_test.view.SplashActivity
import com.example.dai_pc.android_test.view.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by dai_pc on 6/15/2018.
 */
@Module
abstract class ActivityModule{
    @ContributesAndroidInjector
    abstract fun splashActivity():SplashActivity

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun mainActivity():MainActivity
}