package com.onsoim.ledger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(val context: Context, val expenses: List<Expense>) : RecyclerView.Adapter<ExpenseAdapter.Holder>() {
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
        val note = itemView.findViewById<TextView>(R.id.note)
        val account = itemView.findViewById<TextView>(R.id.account)
        val amount = itemView.findViewById<TextView>(R.id.amount)

        fun bind(expense: Expense) {
            note?.text = expense.note
            account.text = expense.account
            amount.text = expense.amount.toString()
        }
    }
}