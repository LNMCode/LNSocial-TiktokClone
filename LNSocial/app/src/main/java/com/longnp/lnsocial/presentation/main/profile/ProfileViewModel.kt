package com.longnp.lnsocial.presentation.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor() : ViewModel() {

    private val TAG = "AppDebug"

    val state: MutableLiveData<ProfileState> = MutableLiveData(ProfileState())

    init {
        onTriggerEvents(ProfileEvents.NewSearch)
    }

    fun onTriggerEvents(events: ProfileEvents) {
        when (events) {
            is ProfileEvents.NewSearch -> {
                //
            }
        }
    }
}