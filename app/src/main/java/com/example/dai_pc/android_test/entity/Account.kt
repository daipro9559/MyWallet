package com.example.dai_pc.android_test.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "accounts")
data class Account(
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "address")
        val address: String,
        @ColumnInfo(name = "note")
        val note: String,
        @ColumnInfo(name = "platform")
        val platform: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}