package com.onsoim.ledger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var expenseDB: ExpenseDB? = null
    private var expenses = listOf<Expense>()
    lateinit var mAdapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expenseDB = ExpenseDB.getInstance(this)
        mAdapter = ExpenseAdapter(this, expenses)

        val r = Runnable {
            try {
                expenses = expenseDB?.expenseDao()?.getAll()!!
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
        ExpenseDB.destroyInstance()
        expenseDB = null
        super.onDestroy()
    }
}