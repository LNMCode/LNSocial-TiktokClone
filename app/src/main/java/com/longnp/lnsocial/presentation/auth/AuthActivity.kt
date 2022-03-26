package com.longnp.lnsocial.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lnsocial.R
import com.example.lnsocial.databinding.ActivityAuthBinding
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

    override fun changeColorNavigation(isHomePage: Boolean) {
        TODO("Not yet implemented")
    }


    private fun subscribeObservers(){
        sessionManager.state.observe(this) { state ->
            displayProgressBar(state.isLoading)
            if (state.didCheckForPreviousAuthUser) {
                onFinishCheckPreviousAuthUser()
            }
            if (state.authToken != null && state.authToken.accountPk != -1) {
                navMainActivity()
            }
        }
    }

    private fun onFinishCheckPreviousAuthUser(){
        binding.fragmentContainer.visibility = View.VISIBLE
        binding.splashLogo.visibility = View.INVISIBLE
    }

    override fun displayProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun navMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}