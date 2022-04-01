package com.longnp.lnsocial.presentation.auth.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor() : ViewModel() {

    private val TAG = "AppDebug"

    val state: MutableLiveData<RegisterState> = MutableLiveData(RegisterState())

    fun onTriggerEvents(events: RegisterEvents) {
        when (events) {
            is RegisterEvents.OnUpdateConfirmPassword -> {
                onUpdateConfirmPassword(events.password)
            }
            is RegisterEvents.OnUpdatePassword -> {
                onUpdatePassword(events.password)
            }
            is RegisterEvents.OnUpdateUsername -> {
                onUpdateUsername(events.username)
            }
            is RegisterEvents.Register -> {
                register()
            }
        }
    }


    private fun onUpdatePassword(password: String) {
        state.value?.let { state ->
            this.state.value = state.copy(password = password)
        }
    }

    private fun onUpdateConfirmPassword(password: String) {
        state.value?.let { state ->
            this.state.value = state.copy(confirmPassword = password)
        }
    }

    private fun onUpdateUsername(username: String) {
        state.value?.let { state ->
            this.state.value = state.copy(username = username)
        }
    }

    private fun register() {

    }
}