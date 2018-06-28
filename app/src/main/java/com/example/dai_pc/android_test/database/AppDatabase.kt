package com.example.dai_pc.android_test.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.dai_pc.android_test.database.dao.TransactionDao
import com.example.dai_pc.android_test.entity.Transaction


//@Database(entities = arrayOf(Transaction::class),version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun transactionDao():TransactionDao
//}