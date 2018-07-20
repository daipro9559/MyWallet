package com.example.dai_pc.android_test

import android.app.Activity
import android.app.Application
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.di.DaggerAppComponent
import com.example.dai_pc.android_test.ultil.PreferenceHelper
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by dai_pc on 6/15/2018.
 */
class MyApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivity: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate() {
        super.onCreate()
        if (Constant.IS_DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        DaggerAppComponent.builder().application(this).build().inject(this)
        if (preferenceHelper.getBoolean(Constant.KEY_OPEN_APP_FIRST) == null
                || !preferenceHelper.getBoolean(Constant.KEY_OPEN_APP_FIRST)) {
            preferenceHelper.putBoolean(Constant.KEY_REQUIRE_PASSWORD, true)
            preferenceHelper.putBoolean(Constant.KEY_OPEN_APP_FIRST, true)
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivity

}