package com.longnp.lnsocial.presentation.main.discovery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.R
import com.longnp.lnsocial.business.interactors.discovery.SearchDiscovery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel
@Inject
constructor(
    private val searchDiscovery: SearchDiscovery,
) : ViewModel() {
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
        state.value?.let { state ->
            searchDiscovery.execute(
                id = R.raw.stories_data
            ).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)
                dataState.data?.let { list ->
                    this.state.value = state.copy(items = list, isLoading = false)
                }

                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }
}