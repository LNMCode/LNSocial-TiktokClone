package com.longnp.lnsocial.presentation.main.inbox.addfriend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
) : ViewModel() {
    private val TAG: String = "AppDebug"

    val state: MutableLiveData<AddFriendState> = MutableLiveData(AddFriendState())

    init {
        onTriggerEvent(AddFriendEvents.GetFriendRecommend)
    }

    private fun onTriggerEvent(event: AddFriendEvents) {
        when (event) {
            is AddFriendEvents.GetFriendRecommend -> {
                getFriendRecommend()
            }
        }
    }

    private fun getFriendRecommend() {
        state.value?.let { state ->
            getFriendRecommend.execute().onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)
                dataState.data?.let { list ->
                    this.state.value = state.copy(friends = list)
                }

                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }

}