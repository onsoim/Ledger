package com.onsoim.ledger.model

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "expenses")
class Expense(@PrimaryKey(autoGenerate = true) var id: Long?,
              @ColumnInfo(name = "date") var date: String,
              @ColumnInfo(name = "account") var account: String,
              @ColumnInfo(name = "category") var category: String,
              @ColumnInfo(name = "amount") var amount: Long,
              @ColumnInfo(name = "remark") var remark: String,
) : Serializable {
    constructor() : this(
        null,
        "",
        "",
        "",
        0L,
        ""
    )

    fun println() {
        Log.d("Expense@ ", "date: $date, account: $account, category: $category, amount: $amount, remark: $remark")
    }
}