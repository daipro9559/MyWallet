package com.example.dai_pc.android_test.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tokenInfo")
data class TokenInfo(
        @PrimaryKey
        @ColumnInfo(name = "address")
        val address: String,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "symbol")
        val symbol: String,
        @ColumnInfo(name = "decimals")
        val decimals: Int,
        @ColumnInfo(name = "addedTime")
        val addedTime: Int,
        @ColumnInfo(name = "network")
        val network: String)