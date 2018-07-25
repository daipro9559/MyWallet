package com.example.dai_pc.android_test.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.dai_pc.android_test.entity.TokenInfo

@Dao
interface TokenDao {

    @Query("SELECT * FROM tokenInfo WHERE address = :myAddress")
    abstract fun getAllToken(myAddress: String): LiveData<TokenInfo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun addToken(tokenInfo: TokenInfo): Long

}