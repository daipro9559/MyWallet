package com.example.dai_pc.android_test.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.dai_pc.android_test.entity.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts WHERE platform = :platform")
    fun getAllWallet(platform: String): LiveData<List<Account>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addWallet(account: Account): Long

    @Query("SELECT * FROM accounts WHERE address = :accountID")
    fun getAccountByAccountId(accountID: String) : LiveData<Account>

    @Delete
    fun deleteWallet(account: Account)
}