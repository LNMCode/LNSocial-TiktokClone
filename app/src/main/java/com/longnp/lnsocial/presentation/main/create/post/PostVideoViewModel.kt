package com.longnp.lnsocial.presentation.main.create.post

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.datasource.local.media.LocalVideo
import com.longnp.lnsocial.business.interactors.post.PostVideo
import com.longnp.lnsocial.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PostVideoViewModel
@Inject
constructor(
    private val postVideo: PostVideo,
    private val sessionManager: SessionManager,
) : ViewModel() {

    val state: MutableLiveData<PostVideoState> = MutableLiveData(PostVideoState())

    fun postVideo(context: Context, localVideo: LocalVideo) {
        state.value?.let { state ->
            val idUser = sessionManager.state.value?.profile?.pk
            if (idUser == null) {
                Log.d("TAG", "postVideo: IdUser is null")
                return
            }
            postVideo.execute(
                context,
                localVideo,
                sessionManager.state.value?.authToken,
                idUser,
                description = state.description
            ).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { data ->
                    this.state.value = state.copy(isSuccess = data.message == "ok")
                }

                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }

    fun cacheData(description: String) {
        state.value?.let { state ->
            this.state.value = state.copy(description = description)
        }
    }

    fun cacheDataHashTags(hashTag: String) {
        state.value?.let { state ->
            this.state.value = state.copy(hashTags = hashTag)
        }
    }
}