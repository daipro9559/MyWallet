package com.example.dai_pc.android_test.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.support.annotation.MainThread
import com.example.dai_pc.android_test.entity.Token

@Dao
interface TokenDao {

    @Query("SELECT * FROM token WHERE address = :myAddress AND network = :networkName")
    fun getAllToken(myAddress: String, networkName: String): LiveData<List<Token>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToken(token: Token): Long

    @Delete()
    fun deleteToken(token: Token):Int

}