package com.longnp.lnsocial.presentation.main.discovery.video

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.longnp.lnsocial.business.domain.models.VideoSeed
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoveryVideoViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val TAG = "AppDebug"

    val state: MutableLiveData<DiscoveryVideoState> = MutableLiveData(DiscoveryVideoState())

    init {
        savedStateHandle.get<Array<VideoSeed>>("videoLink")?.let { data ->
            onTriggerEvent(DiscoveryVideoEvents.GetVideo(data.toList()))
        }
    }

    fun onTriggerEvent(events: DiscoveryVideoEvents) {
        when (events) {
            is DiscoveryVideoEvents.GetVideo -> {
                getVideo(events.data)
            }
        }
    }

    private fun getVideo(data: List<VideoSeed>) {
        state.value?.let { state ->
            this.state.value = state.copy(videoSeedList = data, isLoading = false)
        }
    }
}