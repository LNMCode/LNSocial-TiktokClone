package com.longnp.lnsocial.presentation.main.inbox

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InboxViewModel
@Inject
constructor() : ViewModel() {
    private val TAG = "AppDebug"

    val state: MutableLiveData<InboxState> = MutableLiveData(InboxState())

    init {
        onTriggerEvent(InboxEvents.NewSearch)
    }

    fun onTriggerEvent(events: InboxEvents) {
        when (events) {
            is InboxEvents.NewSearch -> {
                //
            }
        }
    }
}