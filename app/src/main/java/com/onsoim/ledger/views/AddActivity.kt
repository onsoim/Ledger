package com.onsoim.ledger.views

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.onsoim.ledger.R
import com.onsoim.ledger.model.Expense
import com.onsoim.ledger.viewmodel.account.AccountViewModel
import com.onsoim.ledger.views.main.MainActivity
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable

class AddActivity : AppCompatActivity() {
    private lateinit var accountCategory : String
    private lateinit var accountName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setCategory()
        setAccount()
        setButton()
    }

    private fun setCategory() {

    }

    private fun setAccount() {
        val accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        val categories = accountViewModel.allAccountCategory
        for (category : String in categories) {
            val r = RadioButton(this)
            r.text = category
            vD1Account.addView(r)
        }

        vD1Account.setOnCheckedChangeListener { _, checkedId ->
            GlobalScope.launch {
                accountCategory = categories[checkedId - 1]
                val names = accountViewModel.getName(accountCategory)
                runOnUiThread { vD2Account.removeAllViews() }
                for (name : String in names) {
                    val r = RadioButton(this@AddActivity)
                    r.text = name
                    runOnUiThread { vD2Account.addView(r) }
                }

                vD2Account.setOnCheckedChangeListener { _, checkedId ->
                    val r = findViewById<RadioButton>(checkedId)
                    accountName = r.text as String
                }
            }
        }
    }

    private fun setButton() {
        submit.setOnClickListener {
            val newExpense = Expense()
            newExpense.date = date.toString()
            newExpense.vD1Category = vD1Category.text.toString()
            newExpense.vD2Category = vD2Category.text.toString()
            newExpense.vD1Account = accountCategory
            newExpense.vD2Account = accountName
            newExpense.amount = vAmount.text.toString().toLong()
            newExpense.remark = vRemarks.text.toString()

            val replyIntent = Intent(this, MainActivity::class.java)
            replyIntent.putExtra("Expense", newExpense as Serializable)
            setResult(RESULT_OK, replyIntent)

            finish()
        }
    }
}