package com.onsoim.ledger.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.onsoim.ledger.R
import com.onsoim.ledger.model.Expense
import com.onsoim.ledger.model.LedgerDB
import com.onsoim.ledger.views.main.MainActivity
import kotlinx.android.synthetic.main.activity_add.*
import java.io.Serializable

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        submit.setOnClickListener {
            val newExpense = Expense()
            newExpense.date = date.toString()
            newExpense.account = vAccount.text.toString()
            newExpense.category = vD2Category.text.toString()
            newExpense.amount = vAmount.text.toString().toLong()
            newExpense.remark = vRemarks.text.toString()

            val replyIntent = Intent(this, MainActivity::class.java)
            replyIntent.putExtra("Expense", newExpense as Serializable)
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
        }
    }

    override fun onDestroy() {
        LedgerDB.destroyInstance()
        super.onDestroy()
    }
}