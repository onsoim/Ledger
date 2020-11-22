package com.onsoim.ledger.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.onsoim.ledger.model.Expense

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM EXPENSES")
    fun getAll(): LiveData<List<Expense>>

    @Insert(onConflict = REPLACE)
    fun insert(expense: Expense)

    @Query("DELETE FROM EXPENSES")
    fun deleteAll()
}