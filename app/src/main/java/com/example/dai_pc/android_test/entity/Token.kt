package com.example.dai_pc.android_test.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import java.math.BigDecimal

@Entity(tableName = "token")
data class Token(
        @PrimaryKey
        @ColumnInfo(name = "address")
        var address: String,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "symbol")
        val symbol: String,
        @ColumnInfo(name = "decimals")
        val decimals: Int,
        @ColumnInfo(name = "addedTime")
        val addedTime: Long,
        @ColumnInfo(name = "network")
        var network: String,
        @ColumnInfo(name = "balanceEther")
        var balance: String,
        @ColumnInfo(name = "contractAddress")
        val contractAddress: String)
