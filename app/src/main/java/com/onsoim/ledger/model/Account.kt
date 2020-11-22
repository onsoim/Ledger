package com.onsoim.ledger.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "name") var name: String,
) {
    constructor() : this(
        null,
        "",
        "",
    )

    constructor(c: String, n: String) : this(
        null,
        c,
        n,
    )
}