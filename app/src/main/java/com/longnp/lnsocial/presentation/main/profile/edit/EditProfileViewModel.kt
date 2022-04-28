package com.longnp.lnsocial.presentation.main.profile.edit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.interactors.auth.UpdateUsernameFromCache
import com.longnp.lnsocial.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel
@Inject
constructor(
    private val updateUsernameFromCache: UpdateUsernameFromCache,
    private val sessionManager: SessionManager,
) : ViewModel() {

    val state: MutableLiveData<EditProfileState> = MutableLiveData(EditProfileState())

    fun changeUsername() {
        state.value?.let { state ->

            if (state.username == null) {
                Log.d("TAG", "changeUsername: Username is null")
                return
            }
            val pk = sessionManager.state.value?.profile?.pk
            if (pk == null) {
                Log.d("TAG", "changeUsername: PK is null")
                return
            }
            updateUsernameFromCache.execute(
                pk = pk,
                username = state.username,
            ).onEach { dataState ->

                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let {
                    this.state.value = state.copy(
                        profile = it,
                        isLoading = false,
                        isUpdateUI = true,
                        username = null
                    )
                }

            }.launchIn(viewModelScope)
        }
    }

    fun cacheData(username: String) {
        state.value?.let { state ->
            this.state.value = state.copy(username = username)
        }
    }

    fun doneUpdateUI() {
        state.value?.let { state ->
            this.state.value = state.copy(isUpdateUI = false)
        }
    }

}