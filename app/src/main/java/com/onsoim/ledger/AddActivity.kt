package com.onsoim.ledger

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    private var ledgerDB : LedgerDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        ledgerDB = LedgerDB.getInstance(this)

        val addRunnable = Runnable {
            val newExpense = Expense()
            newExpense.date = date.toString()
            newExpense.account = account.text.toString()
            newExpense.category = category.text.toString()
            newExpense.amount = amount.text.toString().toLong()
            newExpense.note = note.text.toString()
            ledgerDB?.expenseDao()?.insert(newExpense)
        }

        submit.setOnClickListener {
            val addThread = Thread(addRunnable)
            addThread.start()

            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onDestroy() {
        LedgerDB.destroyInstance()
        super.onDestroy()
    }
}