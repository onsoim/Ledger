package com.onsoim.ledger.viewmodel.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.onsoim.ledger.model.LedgerDB
import com.onsoim.ledger.model.Account
import kotlinx.coroutines.launch

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AccountRepository
    private val allAccount: LiveData<List<Account>>

    init {
        val accountDao = LedgerDB.getInstance(application, viewModelScope).accountDao()
        repository = AccountRepository(accountDao)
        allAccount = repository.allAccount
    }

    fun insert(account: Account) = viewModelScope.launch {
        repository.insert(account)
    }
}