package com.longnp.lnsocial.presentation.main.seeds.list.item

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.business.interactors.video.CommentVideo
import com.longnp.lnsocial.business.interactors.video.FollowUser
import com.longnp.lnsocial.business.interactors.video.GetCommentsVideo
import com.longnp.lnsocial.business.interactors.video.LikeVideoSeed
import com.longnp.lnsocial.presentation.main.seeds.list.item.SeedItemEvents.*
import com.longnp.lnsocial.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SeedItemViewModel
@Inject
constructor(
    private val sessionManager: SessionManager,
    private val likeVideo: LikeVideoSeed,
    private val followVideo: FollowUser,
    private val getCommentsVideo: GetCommentsVideo,
    private val commentVideo: CommentVideo,
    //private val shareVideo: Int,
) : ViewModel() {

    val state: MutableLiveData<SeedItemState> = MutableLiveData(SeedItemState())

    fun onTriggerEvents(events: SeedItemEvents) {
        when (events) {
            is LikeVideo -> {
                likeVideo()
            }
            is Follow -> {
                followVideo()
            }
            is GetCommentVideo -> {
                getCommentsVideo()
            }
            is Comment -> {
                commentVideo(events.comment)
            }
            is ShareVideo -> {
                shareVideo()
            }
            is InitSeedVideo -> {
                initSeedVideo(events.videoSeed)
            }
            is OnChangeNumberLike -> {
                onChangeNumberLike(events.number)
            }
            is OnChangeNumberComment -> {
                onChangeNumberComment(events.number)
            }
        }
    }

    private fun likeVideo() {
        state.value?.let { state ->
            val pk = state.videoSeed?.pk
            val authToken = sessionManager.state.value?.authToken
            if (pk == null) {
                Log.d("TAG", "likeVideo: pk video is null")
                return
            }
            if (authToken == null) {
                Log.d("TAG", "likeVideo: authToken is null")
                return
            }
            likeVideo.execute(
                pk = pk,
                authToken = authToken,
            ).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { videoSeed ->
                    this.state.value = state.copy(videoSeed = videoSeed)
                    onTriggerEvents(OnChangeNumberLike(videoSeed.numberLike))
                }

                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }

    private fun followVideo() {
        state.value?.let { state ->
            val userFollow = state.videoSeed?.authProfileId
            val authToken = sessionManager.state.value?.authToken
            if (userFollow == null) {
                Log.d("TAG", "likeVideo: pk video is null")
                return
            }
            if (authToken == null) {
                Log.d("TAG", "likeVideo: authToken is null")
                return
            }
            followVideo.execute(
                userFollow = userFollow,
                authToken = authToken,
            ).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { seedItem ->
                    //this.state.value = state.copy(videoSeed = videoSeed)
                }

                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }

    private fun getCommentsVideo() {
        state.value?.let { state ->
            val pk = state.videoSeed?.pk
            val authToken = sessionManager.state.value?.authToken
            if (pk == null) {
                Log.d("TAG", "likeVideo: pk video is null")
                return
            }
            if (authToken == null) {
                Log.d("TAG", "likeVideo: authToken is null")
                return
            }
            getCommentsVideo.execute(
                pk = pk,
                authToken = authToken,
            ).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { comments ->
                    this.state.value = state.copy(comments = comments)
                }

                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }

    private fun commentVideo(comment: String) {
        state.value?.let { state ->
            val pk = state.videoSeed?.pk
            val authToken = sessionManager.state.value?.authToken
            if (pk == null) {
                Log.d("TAG", "likeVideo: pk video is null")
                return
            }
            if (authToken == null) {
                Log.d("TAG", "likeVideo: authToken is null")
                return
            }
            commentVideo.execute(
                pk = pk,
                comment = comment,
                authToken = authToken,
            ).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { comments ->
                    this.state.value = state.copy(comments = comments)
                    onTriggerEvents(OnChangeNumberComment(comments.size))
                }

                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }

    private fun shareVideo() {

    }

    private fun onChangeNumberLike(number: Int){
        state.value?.let { state ->
            this.state.value = state.copy(numberLike = number)
        }
    }

    private fun onChangeNumberComment(number: Int){
        state.value?.let { state ->
            this.state.value = state.copy(numberLike = number)
        }
    }

    private fun initSeedVideo(videoSeed: VideoSeed?) {
        state.value?.let { state ->
            this.state.value = state.copy(videoSeed = videoSeed)
        }
    }
}