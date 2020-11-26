package com.onsoim.ledger.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.onsoim.ledger.model.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM ACCOUNT")
    fun getAll(): LiveData<List<Account>>

    @Query("SELECT * FROM ACCOUNT")
    fun getAllList() : List<Account>

    @Query("SELECT * FROM ACCOUNT WHERE CATEGORY = :category AND NAME = :name")
    fun getAccount(category: String, name: String) : Account

    @Query("SELECT DISTINCT CATEGORY FROM ACCOUNT")
    fun getCategory(): List<String>

    @Query("SELECT NAME FROM ACCOUNT WHERE CATEGORY = :category")
    fun getNames(category: String): List<String>

    @Query("SELECT BALANCE FROM ACCOUNT WHERE NAME = :name")
    fun getBalance(name: String): Long

    @Insert(onConflict = REPLACE)
    suspend fun insert(account: Account)

    @Update
    suspend fun update(account: Account)

    @Query("DELETE FROM ACCOUNT")
    fun deleteAll()
}