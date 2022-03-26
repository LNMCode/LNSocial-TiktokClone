package com.longnp.lnsocial.presentation.main

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.lnsocial.R
import com.example.lnsocial.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.longnp.lnsocial.presentation.BaseActivity
import com.longnp.lnsocial.presentation.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var navController: NavController

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var binding: ActivityMainBinding

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragments_container) as NavHostFragment

        navController = navHostFragment.navController

        setupBottomNavigationView()
        subscribeObservers()
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun subscribeObservers() {
        sessionManager.state.observe(this) { state ->
            displayProgressBar(state.isLoading)
            if (state.authToken == null || state.authToken.accountPk == -1) {
                //navAuthActivity()
            }
        }
    }


    override fun displayProgressBar(isLoading: Boolean) {
    }

    override fun changeColorNavigation(isHomePage: Boolean) {
        val iconColorStates = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ), intArrayOf(
                Color.parseColor(if (isHomePage) "#FFFFFF" else "#123456"),
                Color.parseColor(if (isHomePage) "#FFFFFF" else "#654321")
            )
        )
        bottomNavigationView.itemTextColor = iconColorStates
        bottomNavigationView.itemIconTintList = iconColorStates
        if (isHomePage) bottomNavigationView.setBackgroundColor(
            ContextCompat.getColor(this, android.R.color.transparent)
        ) else {
            bottomNavigationView.setBackgroundColor(Color.WHITE)
        }
    }

    private fun navAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

}