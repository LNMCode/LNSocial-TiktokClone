package com.longnp.lnsocial.presentation.main.discovery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel
@Inject
constructor() : ViewModel() {
    private val TAG: String = "AppDebug"

    val state: MutableLiveData<DiscoveryState> = MutableLiveData(DiscoveryState())

    init {
        onTriggerEvent(DiscoveryEvents.NewSearch)
    }

    fun onTriggerEvent(event: DiscoveryEvents) {
        when (event) {
            is DiscoveryEvents.NewSearch -> {
                search()
            }
        }
    }

    private fun search() {

    }
}