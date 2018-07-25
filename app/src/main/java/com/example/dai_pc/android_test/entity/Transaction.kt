package com.example.dai_pc.android_test.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import java.math.BigInteger

data class Transaction(
        @Expose
        var blockNumber: Long?,
        @Expose
        var timeStamp: String?,
        @Expose
        var hash: String,
        @Expose
        var from: String,
        @Expose
        var to: String,
        @Expose
        var value: String,
        @Expose
        var contractAddress: String,
        @Expose
        var input: String,
        @Expose
        var gas: String,
        @Expose
        var gasPrice: String,
        @Expose
        var gasUsed: String,
        @Expose
        var nonce :String,
        @Expose
        var blockHash: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(blockNumber)
        parcel.writeString(timeStamp)
        parcel.writeString(hash)
        parcel.writeString(from)
        parcel.writeString(to)
        parcel.writeString(value)
        parcel.writeString(contractAddress)
        parcel.writeString(input)
        parcel.writeString(gas)
        parcel.writeString(gasPrice)
        parcel.writeString(gasUsed)
        parcel.writeString(nonce)
        parcel.writeString(blockHash)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }
}