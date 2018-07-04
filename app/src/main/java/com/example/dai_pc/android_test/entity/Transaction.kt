package com.example.dai_pc.android_test.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.math.BigInteger

data class Transaction(

        @Expose
        var blockNumber :Long?,
        @Expose
        var timeStamp:String?,
        @Expose
        var hash:String,
        @Expose
        var from : String,
        @Expose
        var to : String,
        @Expose
        var value : BigInteger,
        @Expose
        var contractAddress :String,
        @Expose
        var input :String,
        @Expose
        var type : String,
        @Expose
        var gas: Long,
        @Expose
        var gasUsed : String,
        @Expose
        var traceId: String,
        @Expose
        var errorCode: String,

        @Expose
        var id : String
)