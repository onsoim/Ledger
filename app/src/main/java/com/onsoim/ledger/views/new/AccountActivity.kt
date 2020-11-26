package com.onsoim.ledger.views.new

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.onsoim.ledger.R
import com.onsoim.ledger.model.Account
import com.onsoim.ledger.viewmodel.account.AccountViewModel
import com.onsoim.ledger.views.main.MainActivity
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : AppCompatActivity() {
       override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        setButton()
    }

    private fun setButton() {
        submit.setOnClickListener {
            val newAccount = Account()
            newAccount.category = vCategory.text.toString()
            newAccount.name = vName.text.toString()
            newAccount.balance = vBalance.text.toString().toLong()

            ViewModelProvider(this).get(AccountViewModel::class.java).insert(newAccount)

            finish()
        }
    }
}