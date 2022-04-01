package com.longnp.lnsocial.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.longnp.lnsocial.databinding.ActivityAuthBinding
import com.longnp.lnsocial.presentation.BaseActivity
import com.longnp.lnsocial.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObservers()
    }

    private fun subscribeObservers(){
        sessionManager.state.observe(this) { state ->
            displayProgressBar(state.isLoading)
            if (state.authToken != null && state.authToken.accountPk != "") {
                navMainActivity()
            }
        }
    }

    private fun onFinishCheckPreviousAuthUser(){
    }

    override fun displayProgressBar(isLoading: Boolean) {

    }

    private fun navMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun changeColorNavigation(isHomePage: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hideNavigation(isHide: Boolean) {
        TODO("Not yet implemented")
    }

}