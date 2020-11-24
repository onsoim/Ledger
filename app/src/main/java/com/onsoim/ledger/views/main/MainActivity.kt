package com.onsoim.ledger.views.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.onsoim.ledger.views.AddActivity
import com.onsoim.ledger.R
import com.onsoim.ledger.model.Expense
import com.onsoim.ledger.model.LedgerDB
import com.onsoim.ledger.viewmodel.expense.ExpenseViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var expenseViewModel: ExpenseViewModel
    private var expenses = listOf<Expense>()
    lateinit var mAdapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setExpense()
    }

    private fun setExpense() {
        mAdapter = ExpenseAdapter(this, expenses)

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)

        expenseViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)
        expenseViewModel.allExpense.observe(this, Observer { expenses ->
            expenses.let { mAdapter.setExpense(it) }
        })

        addBtn.setOnClickListener {
            startActivityForResult(
                Intent(this, AddActivity::class.java),
                1
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.getSerializableExtra("Expense")?.let {
                expenseViewModel.insert(it as Expense)
            }
        }
    }

    override fun onDestroy() {
        LedgerDB.destroyInstance()
        super.onDestroy()
    }
}