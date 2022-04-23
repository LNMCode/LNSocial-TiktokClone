package com.longnp.lnsocial.presentation.main.create.select

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.datasource.local.media.MediaLocalInteraction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectMediaViewModel
@Inject
constructor(
    private val mediaLocalInteraction: MediaLocalInteraction,
) : ViewModel() {

    val state: MutableLiveData<SelectMediaState> = MutableLiveData(SelectMediaState())

    fun onTriggerEvents(events: SelectMediaEvents) {
        when (events) {
            is SelectMediaEvents.GetAllImageAndVideo -> {
                getAllImageAndVideos(events.context)
            }
        }
    }

    private fun getAllImageAndVideos(context: Context) {
        viewModelScope.launch {
            mediaLocalInteraction.getAllVideos(context)
        }
        state.value?.let { state ->
            this.state.value = state.copy(mediaLocalInteraction = mediaLocalInteraction)
        }
    }
}