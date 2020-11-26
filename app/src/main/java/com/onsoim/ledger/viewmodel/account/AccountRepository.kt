package com.onsoim.ledger.viewmodel.account

import androidx.lifecycle.LiveData
import com.onsoim.ledger.model.Account
import com.onsoim.ledger.dao.AccountDao

class AccountRepository(private val accountDao: AccountDao) {
    val allAccount: LiveData<List<Account>> = accountDao.getAll()
    val allAccountCategory: List<String> = accountDao.getCategory()
    fun allAccountName(category: String) = accountDao.getNames(category)
    fun getAccount(category: String, name: String) = accountDao.getAccount(category, name)
    fun getBalance(name: String) = accountDao.getBalance(name)

    suspend fun insert(account: Account) { accountDao.insert(account) }
    suspend fun update(account: Account) { accountDao.update(account) }
}