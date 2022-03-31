package com.longnp.lnsocial.presentation.main.inbox.addfriend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.domain.models.inbox.Friend
import com.longnp.lnsocial.business.domain.util.Constants.Companion.PARAMS_RERQUEST_BODY
import com.longnp.lnsocial.business.domain.util.Constants.Companion.getRequestBodyAuth
import com.longnp.lnsocial.business.interactors.inbox.AddFriendMessage
import com.longnp.lnsocial.business.interactors.inbox.GetFriendRecommend
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddFriendViewModel
@Inject
constructor(
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
            val paramsRequestBody = PARAMS_RERQUEST_BODY
            val bodyRequest = getRequestBodyAuth(paramsRequestBody.toString())
            getFriendRecommend.execute(bodyRequest).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)
                dataState.data?.let { list ->
                    this.state.value = state.copy(friends = list)
                }

                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }

    private fun addFriendMessage(item: Friend) {
        state.value?.let { state ->
            val paramsRequestBody = PARAMS_RERQUEST_BODY
            paramsRequestBody.put("auth_profile_id_other", item.id)
            paramsRequestBody.put("username", item.username)
            paramsRequestBody.put("ava", item.avatar)
            val bodyRequest = getRequestBodyAuth(paramsRequestBody.toString())
            addFriendMessage.execute(
                body = bodyRequest
            ).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)
                dataState.data?.let { inboxModel ->
                    this.state.value = state.copy(inboxModel = inboxModel)
                    this.state.value = state.copy(isAddFriendComplete = true)
                }
                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }
}