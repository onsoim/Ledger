package com.onsoim.ledger.viewmodel.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.onsoim.ledger.model.Account
import com.onsoim.ledger.model.LedgerDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AccountRepository
    private val allAccount: LiveData<List<Account>>
    val allAccountCategory: List<String>

    init {
        val accountDao = LedgerDB.getInstance(application, viewModelScope).accountDao()
        repository = AccountRepository(accountDao)

        allAccount = repository.allAccount
        allAccountCategory = repository.allAccountCategory
    }

    fun insert(account: Account) = viewModelScope.launch {
        repository.insert(account)
    }

    fun update(account: Account) = viewModelScope.launch {
        repository.update(account)
    }

    suspend fun getName(category: String) = withContext(Dispatchers.IO) {
        repository.allAccountName(category)
    }

    suspend fun getAccount(category: String, name: String) = withContext(Dispatchers.IO) {
        repository.getAccount(category, name)
    }

    suspend fun getBalance(name: String) = withContext(Dispatchers.IO) {
        repository.getBalance(name)
    }
}