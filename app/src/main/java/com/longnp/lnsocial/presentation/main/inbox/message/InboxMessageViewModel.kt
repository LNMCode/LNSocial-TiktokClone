package com.longnp.lnsocial.presentation.main.inbox.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.longnp.lnsocial.business.domain.util.StateMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InboxMessageViewModel
@Inject
constructor(

): ViewModel(){

    private val TAG = "AppDebug"

    val state: MutableLiveData<InboxMessageState> = MutableLiveData(InboxMessageState())

    fun onTriggerEvent(events: InboxMessageEvents) {
        when (events) {
            is InboxMessageEvents.GetMessage -> {
                getMessage(events.id)
            }
            is InboxMessageEvents.Send -> {
                send(events.title)
            }
        }
    }

    private fun getMessage(id: String) {

    }

    private fun send(value: String) {

    }
}