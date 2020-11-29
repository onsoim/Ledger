package com.onsoim.ledger.views.new

import android.app.Activity
import android.app.DatePickerDialog
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
import kotlinx.android.synthetic.main.activity_income.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class IncomeActivity : AppCompatActivity() {
    private lateinit var accountCategory : String
    private lateinit var accountName : String
    private val cal = Calendar.getInstance()
    private var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income)

        setDate()
        setAccount()
        setButton()
    }

    private fun setDate() {
        LocalDate.now().toString().also { vDate.text = it }

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        vDate!!.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.KOREA)
        vDate!!.text = sdf.format(cal.time)
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

        val newName = EditText(this@IncomeActivity)
        newName.hint = "new account name"

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
                        val r = RadioButton(this@IncomeActivity)
                        r.text = name
                        runOnUiThread { vD2Account.addView(r) }
                    }
                    runOnUiThread {
                        vD2Account.removeView(newName)
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
            newExpense.vD1Category = ""
            newExpense.vD2Category = vCategory.text.toString()
            newExpense.vD1Account = accountCategory
            newExpense.vD2Account = accountName
            newExpense.amount = vAmount.text.toString().toLong()
            newExpense.remark = vRemarks.text.toString()
            newExpense.println()
            println("FINFIN")
            val replyIntent = Intent(this, MainActivity::class.java)
            replyIntent.putExtra("Income", newExpense as Serializable)
            setResult(Activity.RESULT_OK, replyIntent)

            if (flag) ViewModelProvider(this).get(AccountViewModel::class.java).insert(Account(accountCategory, accountName))

            finish()
        }
    }

    private fun addNewName(newName: EditText) {
        if (vD2Account.childCount == 0 ) {
            runOnUiThread {
                vD2Account.removeView(newName)
                vD2Account.addView(newName)
            }
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