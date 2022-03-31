package com.longnp.lnsocial.presentation.main.inbox.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InboxMessageViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
): ViewModel(){

    private val TAG = "AppDebug"

    val state: MutableLiveData<InboxMessageState> = MutableLiveData(InboxMessageState())

    init {
        savedStateHandle.get<InboxModel>("model")?.let { data ->
            onTriggerEvent(InboxMessageEvents.OnUpdateInboxModel(data))
        }
    }

    fun onTriggerEvent(events: InboxMessageEvents) {
        when (events) {
            is InboxMessageEvents.OnUpdateInboxModel -> {
                onUpdateInboxModel(events.inboxModel)
            }
            is InboxMessageEvents.GetMessage -> {
                getMessage(events.id)
            }
            is InboxMessageEvents.Send -> {
                send(events.title)
            }
        }
    }

    private fun onUpdateInboxModel(inboxModel: InboxModel) {
        state.value?.let { state ->
            this.state.value = state.copy(inboxModel = inboxModel)
        }
    }

    private fun getMessage(id: String) {

    }

    private fun send(value: String) {

    }
}