package com.longnp.lnsocial.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.longnp.lnsocial.databinding.ActivityAuthBinding
import com.longnp.lnsocial.presentation.BaseActivity
import com.longnp.lnsocial.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    private lateinit var binding: ActivityAuthBinding

    private val flowDelay = flow {
        delay(2000)
        emit(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        sessionManager.state.observe(this) { state ->
            displayProgressBar(state.isLoading)
            if (state.didCheckForPreviousAuthUser) {
                onFinishCheckPreviousAuthUser()
            }
            if (state.authToken != null &&
                state.profile != null &&
                state.authToken.accountPk != "" &&
                state.profile.pk != ""
            ) {
                navMainActivity()
            }
        }
    }

    override fun displayProgressBar(isLoading: Boolean) {
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }
        else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun navMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun onFinishCheckPreviousAuthUser() {
        lifecycleScope.launchWhenCreated {
            flowDelay.collect {
                binding.layoutSplash.root.isVisible = false
            }
        }
    }

    override fun changeColorNavigation(isHomePage: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hideNavigation(isHide: Boolean) {
        TODO("Not yet implemented")
    }

}