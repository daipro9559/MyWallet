package com.example.dai_pc.android_test.di

import android.app.Application
import android.content.Context
import com.android.example.github.util.LiveDataCallAdapterFactory
import com.example.dai_pc.android_test.base.Constant
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.ethereum.geth.Geth
import org.ethereum.geth.KeyStore
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

/**
 * Created by dai_pc on 6/15/2018.
 */
@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun applicationContext(app: Application): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun keyStore(context: Context): KeyStore {
        val file = File(context.getFilesDir(), "keystore/keystore")
        var keyStore = KeyStore(file.getAbsolutePath(), Geth.LightScryptN, Geth.LightScryptP)
        return keyStore
    }

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .build()
    }

}