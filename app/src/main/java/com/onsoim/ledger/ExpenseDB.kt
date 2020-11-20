package com.onsoim.ledger

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Expense::class], version = 1)
abstract class ExpenseDB: RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        private var INSTANCE: ExpenseDB? = null

        fun getInstance(context: Context): ExpenseDB? {
            if (INSTANCE == null) {
                synchronized(ExpenseDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ExpenseDB::class.java,
                        "expense.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}