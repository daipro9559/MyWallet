package com.example.dai_pc.android_test.di

import android.app.Application
import com.example.dai_pc.android_test.MyApp
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by dai_pc on 6/15/2018.
 */
@Singleton
@Component (modules =  [AndroidInjectionModule::class,AndroidSupportInjectionModule::class,AppModule::class, ActivityModule::class])
interface AppComponent{
    @Component.Builder
    interface Builder{
        @BindsInstance
       fun application(app : Application): Builder
        fun build(): AppComponent
    }
    fun inject(myApp: MyApp)
}