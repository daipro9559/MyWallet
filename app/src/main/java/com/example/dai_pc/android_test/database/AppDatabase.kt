package com.example.dai_pc.android_test.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.dai_pc.android_test.database.dao.TokenDao
import com.example.dai_pc.android_test.entity.TokenInfo
import com.example.dai_pc.android_test.entity.Transaction


@Database(entities = arrayOf(TokenInfo::class),version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}