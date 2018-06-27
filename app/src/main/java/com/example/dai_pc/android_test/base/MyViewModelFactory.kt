package com.example.dai_pc.android_test.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by dai_pc on 6/15/2018.
 */
@Singleton
class MyViewModelFactory @Inject constructor(
        val creators : Map<Class<out ViewModel>,Provider<ViewModel>>
): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        var creator = creators[modelClass] ?: creators.entries.firstOrNull{
            modelClass.isAssignableFrom(it.key)
        } ?.value ?: throw IllegalArgumentException("unknown model class $modelClass")

        @Suppress("UNCHECKED_CAST")
        return creator as T

    }


}