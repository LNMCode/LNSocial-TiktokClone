package com.longnp.lnsocial.presentation.main.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.interactors.auth.ProfileFromCache
import com.longnp.lnsocial.business.interactors.profile.GetVideoByType
import com.longnp.lnsocial.presentation.main.profile.TabAdapter.*
import com.longnp.lnsocial.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val profileFromCache: ProfileFromCache,
    private val getVideoByType: GetVideoByType,
) : ViewModel() {

    private val TAG = "AppDebug"

    val state: MutableLiveData<ProfileState> = MutableLiveData(ProfileState())

    init {
        onTriggerEvents(ProfileEvents.GetProfile)
    }

    fun onTriggerEvents(events: ProfileEvents) {
        when (events) {
            is ProfileEvents.GetProfile -> {
                getProfile()
            }
            is ProfileEvents.GetVideoByType -> {
                getVideoByType(events.type)
            }
        }
    }

    private fun getProfile() {
        state.value?.let { state ->
            val authId = sessionManager.state.value?.authToken?.authProfileId
            if (authId == null) {
                Log.d(TAG, "getProfile: GetProfile authid is null")
                return
            }
            profileFromCache.execute(authId).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { profile ->
                    this.state.value = state.copy(profile = profile, isLoading = false)
                    onTriggerEvents(ProfileEvents.GetVideoByType(PUBLIC.name))
                }

                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }

    private fun getVideoByType(type: String) {
        state.value?.let { state ->
            val authToken = sessionManager.state.value?.authToken
            getVideoByType.execute(
                authToken = authToken,
                type = type
            ).onEach { dataState ->
                this.state.value = state.copy(dataState.isLoading)

                dataState.data?.let { list ->
                    when (type.uppercase()) {
                        PUBLIC.name -> {
                            this.state.value = state.copy(videoPublic = list, isLoading = false)
                        }
                        FAVORITE.name -> {
                            this.state.value = state.copy(videoFavorite = list, isLoading = false)
                        }
                        PRIVATE.name -> {
                            this.state.value = state.copy(videoPrivate = list, isLoading = false)
                        }
                    }
                }

                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }
}