package com.onsoim.ledger.views.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.onsoim.ledger.views.new.ExpenseActivity
import com.onsoim.ledger.R
import com.onsoim.ledger.model.Expense
import com.onsoim.ledger.model.LedgerDB
import com.onsoim.ledger.viewmodel.account.AccountViewModel
import com.onsoim.ledger.viewmodel.expense.ExpenseViewModel
import com.onsoim.ledger.views.new.AccountActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.exp

class MainActivity : AppCompatActivity() {
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var expenseViewModel: ExpenseViewModel
    private var expenses = listOf<Expense>()
    lateinit var mAdapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setButton()
    }

    private fun setButton() {
        setAccount()
        setExpense()
    }

    private fun setAccount() {
        newAccount.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }
    }

    private fun setExpense() {
        mAdapter = ExpenseAdapter(this, expenses)

        mRecyclerView.adapter = mAdapter
        val manager = LinearLayoutManager(this)
        manager.reverseLayout = true
        manager.stackFromEnd = true
        mRecyclerView.layoutManager = manager
        mRecyclerView.setHasFixedSize(true)

        expenseViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)
        expenseViewModel.allExpense.observe(this, Observer { expenses ->
            expenses.let { mAdapter.setExpense(it) }
        })

        newExpense.setOnClickListener {
            startActivityForResult(
                Intent(this, ExpenseActivity::class.java),
                1
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.getSerializableExtra("Expense")?.let {
                val expense = it as Expense

                accountViewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
                GlobalScope.launch {
                    val account = accountViewModel.getAccount(expense.vD1Account, expense.vD2Account)
                    account.balance -= expense.amount
                    accountViewModel.update(account)
                }
                expenseViewModel.insert(expense)
            }
        }
    }

    override fun onDestroy() {
        LedgerDB.destroyInstance()
        super.onDestroy()
    }
}