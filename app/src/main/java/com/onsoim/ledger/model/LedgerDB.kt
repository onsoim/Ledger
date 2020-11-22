package com.onsoim.ledger.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.onsoim.ledger.dao.AccountDao
import com.onsoim.ledger.dao.ExpenseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    version = 1,
    entities = [
        Account::class,
        Expense::class,
    ],
    exportSchema = false,
)
abstract class LedgerDB: RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun expenseDao(): ExpenseDao

    private class LedgerDBCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val accountDao = database.accountDao()
                    accountDao.insert(
                        Account("Cash", "Wallet")
                    )
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LedgerDB? = null

        fun getInstance(context: Context, scope: CoroutineScope): LedgerDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LedgerDB::class.java,
                    "LedgerDB"
                )
                    .addCallback(LedgerDBCallback(scope))
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}