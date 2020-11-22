package com.onsoim.ledger.viewmodel.expense

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.onsoim.ledger.model.LedgerDB
import com.onsoim.ledger.model.Expense
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ExpenseRepository
    val allExpense: LiveData<List<Expense>>

    init {
        val expenseDao = LedgerDB.getInstance(application, viewModelScope).expenseDao()
        repository = ExpenseRepository(expenseDao)
        allExpense = repository.allExpense
    }

    fun insert(expense: Expense) = viewModelScope.launch {
        repository.insert(expense)
    }
}