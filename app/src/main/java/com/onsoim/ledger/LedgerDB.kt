package com.onsoim.ledger

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        Expense::class,
    ]
)
abstract class LedgerDB: RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        private var INSTANCE: LedgerDB? = null

        fun getInstance(context: Context): LedgerDB? {
            if (INSTANCE == null) {
                synchronized(LedgerDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LedgerDB::class.java,
                        "Ledger.db")
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