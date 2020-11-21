package com.onsoim.ledger.view.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.onsoim.ledger.R
import com.onsoim.ledger.model.Expense.Expense

class ExpenseAdapter(private val context: Context, private val expenses: List<Expense>) : RecyclerView.Adapter<ExpenseAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(expenses[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val note: TextView = itemView.findViewById(R.id.note)
        private val account: TextView = itemView.findViewById(R.id.account)
        private val amount: TextView = itemView.findViewById(R.id.amount)

        fun bind(expense: Expense) {
            note.text = expense.note
            account.text = expense.account
            amount.text = expense.amount.toString()
        }
    }
}