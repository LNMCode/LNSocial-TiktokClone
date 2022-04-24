package com.longnp.lnsocial.presentation.main.inbox.addfriend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.domain.models.inbox.Friend
import com.longnp.lnsocial.business.domain.util.Constants.Companion.getRequestBodyAuth
import com.longnp.lnsocial.business.interactors.inbox.AddFriendMessage
import com.longnp.lnsocial.business.interactors.inbox.GetFriendRecommend
import com.longnp.lnsocial.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddFriendViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val getFriendRecommend: GetFriendRecommend,
    private val addFriendMessage: AddFriendMessage,
) : ViewModel() {
    private val TAG: String = "AppDebug"

    val state: MutableLiveData<AddFriendState> = MutableLiveData(AddFriendState())

    init {
        onTriggerEvent(AddFriendEvents.GetFriendRecommend)
    }

    fun onTriggerEvent(event: AddFriendEvents) {
        when (event) {
            is AddFriendEvents.GetFriendRecommend -> {
                getFriendRecommend()
            }
            is AddFriendEvents.AddFriendMessage -> {
                addFriendMessage(event.item)
            }
        }
    }

    private fun getFriendRecommend() {
        state.value?.let { state ->
            getFriendRecommend.execute(sessionManager.state.value?.authToken).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)
                dataState.data?.let { list ->
                    this.state.value = state.copy(friends = list, isLoading = false)
                }

                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }

    private fun addFriendMessage(item: Friend) {
        state.value?.let { state ->
            addFriendMessage.execute(
                authToken = sessionManager.state.value?.authToken,
                authProfileIdOther = item.id,
                username = item.username,
                ava = item.avatar
            ).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)
                dataState.data?.let { inboxModel ->
                    this.state.value = state.copy(inboxModel = inboxModel)
                    this.state.value = state.copy(isAddFriendComplete = true, isLoading = false)
                }
                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }
}