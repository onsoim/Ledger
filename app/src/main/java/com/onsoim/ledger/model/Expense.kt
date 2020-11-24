package com.onsoim.ledger.model

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "expenses")
class Expense(@PrimaryKey(autoGenerate = true) var id: Long?,
              @ColumnInfo(name = "date") var date: String,
              @ColumnInfo(name = "vD1Account") var vD1Account: String,
              @ColumnInfo(name = "vD2Account") var vD2Account: String,
              @ColumnInfo(name = "vD1Category") var vD1Category: String,
              @ColumnInfo(name = "vD2Category") var vD2Category: String,
              @ColumnInfo(name = "amount") var amount: Long,
              @ColumnInfo(name = "remark") var remark: String,
) : Serializable {
    constructor() : this(
        null,
        "",
        "",
        "",
        "",
        "",
        0L,
        ""
    )

    fun println() {
        Log.d("Expense@ ",
            "date: $date, " +
                "vD1Account: $vD1Account, vD2Account: $vD2Account, " +
                "vD1Category: $vD1Category, vD2Category: $vD2Category, " +
                "amount: $amount, remark: $remark"
        )
    }
}