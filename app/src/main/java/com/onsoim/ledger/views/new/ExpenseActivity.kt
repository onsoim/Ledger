package com.onsoim.ledger.views.new

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.onsoim.ledger.R
import com.onsoim.ledger.model.Account
import com.onsoim.ledger.model.Expense
import com.onsoim.ledger.viewmodel.account.AccountViewModel
import com.onsoim.ledger.views.main.MainActivity
import kotlinx.android.synthetic.main.activity_expense.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable

class ExpenseActivity : AppCompatActivity() {
    private lateinit var accountCategory : String
    private lateinit var accountName : String
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

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

        val newCategory = EditText(this)
        newCategory.hint = "new account category"
        vD1Account.addView(newCategory)

        val newName = EditText(this@ExpenseActivity)
        newName.hint = "new account name"
        // newName.text = null

        newCategory.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (vD2Account.getChildAt(0) is  RadioButton) {
                    runOnUiThread { vD2Account.removeAllViews() }
                    vD1Account.clearCheck()
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                accountCategory = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrEmpty()) addNewName(newName)
            }
        })

        vD1Account.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                if (!newCategory.text.isNullOrEmpty()) newCategory.text = null
                flag = false

                GlobalScope.launch {
                    runOnUiThread {
                        newName.text = null
                        vD2Account.removeAllViews()
                    }

                    val c = findViewById<RadioButton>(checkedId)
                    accountCategory = c.text.toString()
                    for (name: String in accountViewModel.getName(accountCategory)) {
                        val r = RadioButton(this@ExpenseActivity)
                        r.text = name
                        runOnUiThread { vD2Account.addView(r) }
                    }
                    runOnUiThread {
                        vD2Account.removeView(newName)
                        vD2Account.addView(newName)
                    }
                    addNewName(newName)

                    vD2Account.setOnCheckedChangeListener { _, checkedId ->
                        val r = findViewById<RadioButton>(checkedId)
                        accountName = r.text.toString()
                    }
                }
            }
        }
    }

    private fun setButton() {
        submit.setOnClickListener {
            val newExpense = Expense()
            newExpense.date = vDate.text.toString()
            newExpense.vD1Category = vD1Category.text.toString()
            newExpense.vD2Category = vD2Category.text.toString()
            newExpense.vD1Account = accountCategory
            newExpense.vD2Account = accountName
            newExpense.amount = vAmount.text.toString().toLong()
            newExpense.remark = vRemarks.text.toString()

            val replyIntent = Intent(this, MainActivity::class.java)
            replyIntent.putExtra("Expense", newExpense as Serializable)
            setResult(RESULT_OK, replyIntent)

            if (flag) ViewModelProvider(this).get(AccountViewModel::class.java).insert(Account(accountCategory, accountName))

            finish()
        }
    }

    private fun addNewName(newName: EditText) {
        if (vD2Account.childCount == 0 ) {
            vD2Account.addView(newName)
        }

        newName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                accountName = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                flag = !p0.isNullOrBlank()
            }
        })
    }
}