package com.onsoim.ledger.model.Expense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
class Expense(@PrimaryKey(autoGenerate = true) var id: Long?,
              @ColumnInfo(name = "date") var date: String,
              @ColumnInfo(name = "account") var account: String,
              @ColumnInfo(name = "category") var category: String,
              @ColumnInfo(name = "amount") var amount: Long,
              @ColumnInfo(name = "remarks") var remark: String,
) {
    constructor() : this(
        null,
        "20201120",
        "American Bank",
        "Coffee",
        3500,
        "Starbucks"
    )
}