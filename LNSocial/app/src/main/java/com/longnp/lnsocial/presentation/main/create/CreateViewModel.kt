package com.longnp.lnsocial.presentation.main.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateViewModel
@Inject
constructor() : ViewModel() {
    private val TAG: String = "AppDebug"

    val state: MutableLiveData<CreateState> = MutableLiveData(CreateState())

    init {
        onTriggerEvent(CreateEvents.NewSearch)
    }

    fun onTriggerEvent(event: CreateEvents) {
        when (event) {
            is CreateEvents.NewSearch -> {
                search()
            }
        }
    }

    private fun search() {

    }
}