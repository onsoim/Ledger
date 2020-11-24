package com.onsoim.ledger.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.onsoim.ledger.model.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM ACCOUNT")
    fun getAll(): LiveData<List<Account>>

    @Query("SELECT * FROM ACCOUNT")
    fun getAllList() : List<Account>

    @Query("SELECT DISTINCT CATEGORY FROM ACCOUNT")
    fun getCategory(): List<String>

    @Query("SELECT NAME FROM ACCOUNT WHERE CATEGORY = :category")
    fun getName(category: String): List<String>

    @Insert(onConflict = REPLACE)
    suspend fun insert(account: Account)

    @Query("DELETE FROM ACCOUNT")
    fun deleteAll()
}