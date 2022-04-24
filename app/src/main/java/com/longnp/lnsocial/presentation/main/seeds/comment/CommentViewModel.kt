package com.longnp.lnsocial.presentation.main.seeds.comment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.interactors.video.AddCommentVideo
import com.longnp.lnsocial.business.interactors.video.GetCommentsVideo
import com.longnp.lnsocial.presentation.main.seeds.comment.CommentEvents.*
import com.longnp.lnsocial.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CommentViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val getCommentsVideo: GetCommentsVideo,
    private val addCommentVideo: AddCommentVideo,
) : ViewModel() {

    val state: MutableLiveData<CommentState> = MutableLiveData(CommentState())

    fun onTriggerEvents(events: CommentEvents) {
        when (events) {
            is GetComment -> {
                getComment()
            }
            is AddComment -> {
                addComment()
            }
            is OnChangeComment -> {
                onChangeComment(events.comment)
            }
        }
    }

    private fun getComment() {
        state.value?.let { state ->
            if (state.videoId != null) {
                val authToken = sessionManager.state.value?.authToken
                if (authToken == null) {
                    Log.d("TAG", "addComment: authToken is null")
                    return
                }
                getCommentsVideo.execute(
                    pk = state.videoId,
                    authToken = authToken
                ).onEach { dataState ->
                    this.state.value = state.copy(isLoading = dataState.isLoading)

                    dataState.data?.let { list ->
                        this.state.value = state.copy(comments = list, isLoading = false)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun addComment() {
        state.value?.let { state ->
            if (state.videoId != null) {
                val authToken = sessionManager.state.value?.authToken
                if (authToken == null) {
                    Log.d("TAG", "addComment: authToken is null")
                    return
                }
                addCommentVideo.execute(
                    pk = state.videoId,
                    comment = state.comment ?: "",
                    authToken = authToken
                ).onEach { dataState ->
                    this.state.value = state.copy(isLoading = dataState.isLoading)

                    dataState.data?.let { list ->
                        this.state.value = state.copy(comments = list, isLoading = false)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun onChangeComment(comment: String) {
        state.value?.let { state ->
            this.state.value = state.copy(comment = comment)
        }
    }

    fun setVideoId(id: String) {
        state.value?.let { state ->
            this.state.value = state.copy(videoId = id)
        }
    }

}