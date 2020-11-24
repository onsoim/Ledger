package com.onsoim.ledger.viewmodel.account

import androidx.lifecycle.LiveData
import com.onsoim.ledger.model.Account
import com.onsoim.ledger.dao.AccountDao

class AccountRepository(private val accountDao: AccountDao) {
    val allAccount: LiveData<List<Account>> = accountDao.getAll()
    val allAccountCategory: List<String> = accountDao.getCategory()
    fun allAccountName(category: String) = accountDao.getName(category)

    suspend fun insert(account: Account) { accountDao.insert(account) }
}