package com.onsoim.ledger.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.onsoim.ledger.R
import com.onsoim.ledger.model.Expense.Expense
import com.onsoim.ledger.model.LedgerDB
import com.onsoim.ledger.view.main.MainActivity
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
            newExpense.account = vAccount.text.toString()
            newExpense.category = vD2Category.text.toString()
            newExpense.amount = vAmount.text.toString().toLong()
            newExpense.remark = vRemarks.text.toString()
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