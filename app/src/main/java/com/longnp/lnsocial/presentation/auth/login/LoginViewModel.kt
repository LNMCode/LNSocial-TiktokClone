package com.longnp.lnsocial.presentation.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.interactors.auth.Login
import com.longnp.lnsocial.presentation.session.SessionEvents
import com.longnp.lnsocial.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val login: Login,
    private val sessionManager: SessionManager,
) : ViewModel() {
    private val TAG = "AppDebug"

    val state: MutableLiveData<LoginState> = MutableLiveData(LoginState())

    fun onTriggerEvents(events: LoginEvents) {
        when (events) {
            is LoginEvents.Login -> {
                login()
            }
            is LoginEvents.OnUpdateUsername -> {
                onUpdateUsername(events.username)
            }
            is LoginEvents.OnUpdatePassword -> {
                onUpdatePassword(events.password)
            }
        }
    }

    private fun login() {
        state.value?.let { state ->
            val username = state.username
            val password = state.password

            if (username.isNotBlank() && password.isNotBlank()) {
                login.execute(
                    username = username,
                    password = password,
                ).onEach { dataState ->
                    this.state.value = state.copy(isLoading = dataState.isLoading)

                    dataState.data?.let { authToken ->
                        sessionManager.onTriggerEvent(SessionEvents.Login(authToken))
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun onUpdateUsername(username: String) {
        state.value?.let { state ->
            this.state.value = state.copy(username = username)
        }
    }

    private fun onUpdatePassword(password: String) {
        state.value?.let { state ->
            this.state.value = state.copy(password = password)
        }
    }
}