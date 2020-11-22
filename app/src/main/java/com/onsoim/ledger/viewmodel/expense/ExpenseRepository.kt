package com.onsoim.ledger.viewmodel.expense

import androidx.lifecycle.LiveData
import com.onsoim.ledger.model.Expense
import com.onsoim.ledger.dao.ExpenseDao

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    val allExpense : LiveData<List<Expense>> = expenseDao.getAll()

    suspend fun insert(expense: Expense) { expenseDao.insert(expense) }
}