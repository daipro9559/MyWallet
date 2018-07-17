package com.example.dai_pc.android_test.di

import android.app.Application
import android.content.Context
import com.example.dai_pc.android_test.base.Constant
import com.example.dai_pc.android_test.repository.NetworkRepository
import com.example.dai_pc.android_test.service.AccountService
import com.example.dai_pc.android_test.service.AccountServiceImp
import com.example.dai_pc.android_test.ultil.PreferenceHelper
//import com.example.dai_pc.android_test.database.AppDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.ethereum.geth.Geth
import org.ethereum.geth.KeyStore
import java.io.File
import java.util.concurrent.TimeUnit
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
        val file = File(context.getFilesDir(), Constant.KEY_STORE_FILE_CHILD)
        var keyStore = KeyStore(file.getAbsolutePath(), Geth.LightScryptN, Geth.LightScryptP)
        return keyStore
    }

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor())
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()
    }

    @Singleton
    @Provides
    fun serviceAccount(accountServiceImp: AccountServiceImp): AccountService {
        return accountServiceImp
    }

//    @Singleton
//    @Provides
//    fun networkRepository(preferenceHelper: PreferenceHelper,context: Context): NetworkRepository{
//        return NetworkRepository(preferenceHelper,context)
//    }

//    @Singleton
//    @Provides
//    fun appDatabase(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, Constant.APP_DATABASE_NAME).build()

}