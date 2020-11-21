package com.onsoim.ledger.model.Expense

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM EXPENSES")
    fun getAll(): List<Expense>

    @Insert(onConflict = REPLACE)
    fun insert(expense: Expense)

    @Query("DELETE FROM EXPENSES")
    fun deleteAll()
}