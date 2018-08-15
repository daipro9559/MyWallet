package com.example.dai_pc.android_test.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.dai_pc.android_test.database.dao.TokenDao
import com.example.dai_pc.android_test.database.dao.AccountDao
import com.example.dai_pc.android_test.entity.Token
import com.example.dai_pc.android_test.entity.Account


@Database(entities = [Token::class,Account::class],version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
    abstract fun walletDao() : AccountDao
}