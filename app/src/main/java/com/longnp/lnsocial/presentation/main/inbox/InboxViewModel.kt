package com.longnp.lnsocial.presentation.main.inbox

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.domain.util.Constants
import com.longnp.lnsocial.business.interactors.inbox.GetConnectedMessage
import com.longnp.lnsocial.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class InboxViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val getConnectedMessage: GetConnectedMessage,
) : ViewModel() {
    private val TAG = "AppDebug"

    val state: MutableLiveData<InboxState> = MutableLiveData(InboxState())

    init {
        onTriggerEvent(InboxEvents.NewSearch)
    }

    fun onTriggerEvent(events: InboxEvents) {
        when (events) {
            is InboxEvents.NewSearch -> {
                getConnectedFriend()
            }
        }
    }

    private fun getConnectedFriend() {
        state.value?.let { state ->
            getConnectedMessage.execute(sessionManager.state.value?.authToken).onEach {  dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { list ->
                    this.state.value = state.copy(inboxModelList = list, isLoading = false)
                }
                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }
}