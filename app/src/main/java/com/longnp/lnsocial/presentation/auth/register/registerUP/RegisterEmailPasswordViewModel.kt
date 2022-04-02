package com.longnp.lnsocial.presentation.auth.register.registerUP

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.interactors.auth.Register
import com.longnp.lnsocial.presentation.session.SessionEvents
import com.longnp.lnsocial.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterEmailPasswordViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val register: Register,
) : ViewModel() {

    private val TAG = "AppDebug"

    val state: MutableLiveData<RegisterEmailPasswordState> =
        MutableLiveData(RegisterEmailPasswordState())

    fun onTriggerEvents(events: RegisterEmailPasswordEvents) {
        when (events) {
            is RegisterEmailPasswordEvents.Register -> {
                register()
            }
            is RegisterEmailPasswordEvents.OnUpdateUsername -> {
                onUpdateUsername(events.username)
            }
            is RegisterEmailPasswordEvents.OnUpdatePassword -> {
                onUpdatePassword(events.password)
            }
            is RegisterEmailPasswordEvents.OnUpdateConfirmPassword -> {
                onUpdateConfirmPassword(events.password)
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
        state.value?.let { state ->
            register.execute(
                username = state.username,
                password = state.password,
            ).onEach { dataSate ->
                this.state.value = state.copy(isLoading = dataSate.isLoading)

                dataSate.data?.let { authToken ->
                    sessionManager.onTriggerEvent(SessionEvents.Login(authToken))
                }

                dataSate.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }

}