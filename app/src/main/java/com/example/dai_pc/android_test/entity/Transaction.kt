package com.example.dai_pc.android_test.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
data class Transaction(

//        @Expose
//        var operations:String?,
        @Expose
        var contract :String?,
        @Expose
        var _id:String?,
        @Expose
        var blockNumber:Long,
        @Expose
        var timeStamp : String,
        @Expose
        var nonce : Long,
        @Expose
        var from : String,
        @Expose
        var to :String,
        @Expose
        var value :String,
        @Expose
        var gas : String,
        @Expose
        var gasPrice: String,
        @Expose
        var gasUsed : String,
        @Expose
        var input: String,
        @Expose
        var error: String,

        @Expose
        var id : String
)