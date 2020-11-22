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

    @Insert(onConflict = REPLACE)
    suspend fun insert(account: Account)

    @Query("DELETE FROM ACCOUNT")
    fun deleteAll()
}