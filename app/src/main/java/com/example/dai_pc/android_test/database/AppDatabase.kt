package com.example.dai_pc.android_test.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.dai_pc.android_test.database.dao.TokenDao
import com.example.dai_pc.android_test.entity.Token


@Database(entities = [Token::class],version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}