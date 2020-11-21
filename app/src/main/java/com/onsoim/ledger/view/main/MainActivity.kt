package com.onsoim.ledger.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.onsoim.ledger.view.AddActivity
import com.onsoim.ledger.R
import com.onsoim.ledger.model.Expense.Expense
import com.onsoim.ledger.model.LedgerDB
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var ledgerDB: LedgerDB? = null
    private var expenses = listOf<Expense>()
    lateinit var mAdapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ledgerDB = LedgerDB.getInstance(this)
        mAdapter = ExpenseAdapter(this, expenses)

        val r = Runnable {
            try {
                expenses = ledgerDB?.expenseDao()?.getAll()!!
                mAdapter = ExpenseAdapter(this, expenses)
                mAdapter.notifyDataSetChanged()

                mRecyclerView.adapter = mAdapter
                mRecyclerView.layoutManager = LinearLayoutManager(this)
                mRecyclerView.setHasFixedSize(true)
            } catch (e: Exception) {
                Log.d("tag", "Error - $e")
            }
        }

        val thread = Thread(r)
        thread.start()

        addBtn.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        LedgerDB.destroyInstance()
        ledgerDB = null
        super.onDestroy()
    }
}