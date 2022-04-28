package com.longnp.lnsocial.presentation.main.seeds.list.item

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.business.interactors.auth.ProfileFromCache
import com.longnp.lnsocial.business.interactors.video.CommentVideo
import com.longnp.lnsocial.business.interactors.video.FollowUser
import com.longnp.lnsocial.business.interactors.video.GetCommentsVideo
import com.longnp.lnsocial.business.interactors.video.LikeVideoSeed
import com.longnp.lnsocial.presentation.main.seeds.list.item.SeedItemEvents.*
import com.longnp.lnsocial.presentation.session.SessionEvents
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
    private val profileFromCache: ProfileFromCache,
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
            is OnChangeIsLike -> {
                onChangeIsLike()
            }
            is CheckFollowing -> {
                checkFollowing()
            }
        }
    }

    private fun likeVideo() {
        state.value?.let { state ->
            val pk = state.videoSeed?.pk
            val favoritesList = sessionManager.state.value?.profile?.favoriteVideos
            val authToken = sessionManager.state.value?.authToken
            if (pk == null) {
                Log.d("TAG", "likeVideo: pk video is null")
                return
            }
            if (authToken == null) {
                Log.d("TAG", "likeVideo: authToken is null")
                return
            }
            if (favoritesList == null) {
                Log.d("TAG", "likeVideo: favoritesList is empty")
                return
            }
            val isLike = favoritesList.contains(pk)

            likeVideo.execute(
                pk = pk,
                isLike = isLike,
                favoritesList = favoritesList,
                authToken = authToken,
            ).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { videoSeed ->
                    this.state.value = state.copy(videoSeed = videoSeed, isLoading = false)
                    sessionManager.onTriggerEvent(SessionEvents.OnGetProfileFromCache(authToken.authProfileId))
                    onTriggerEvents(OnChangeNumberLike(videoSeed.numberLike))
                    onTriggerEvents(OnChangeIsLike)
                }

                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }

    private fun followVideo() {
        state.value?.let { state ->
            val userFollow = state.videoSeed?.authProfileId
            val followingList = sessionManager.state.value?.profile?.following
            val authToken = sessionManager.state.value?.authToken
            if (userFollow == null) {
                Log.d("TAG", "likeVideo: userFollow is null")
                return
            }
            if (authToken == null) {
                Log.d("TAG", "likeVideo: authToken is null")
                return
            }
            if (followingList == null) {
                Log.d("TAG", "likeVideo: favoritesList is empty")
                return
            }
            followVideo.execute(
                userFollow = userFollow,
                followingList = followingList,
                authToken = authToken,
            ).onEach { dataState ->
                this.state.value = state.copy(isLoading = dataState.isLoading)

                dataState.data?.let { seedItem ->
                    this.state.value = state.copy(isFollow = true, isLoading = false)
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
                    this.state.value = state.copy(comments = comments, isLoading = false)
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
                    this.state.value = state.copy(comments = comments, isLoading = false)
                    onTriggerEvents(OnChangeNumberComment(comments.size))
                }

                dataState.stateMessage?.let {}
            }.launchIn(viewModelScope)
        }
    }

    private fun onChangeIsLike() {
        state.value?.let { state ->
            val pk = sessionManager.state.value?.profile?.pk
            if (pk != null) {
                profileFromCache.execute(pk).onEach { dataState ->
                    val favoritesList = dataState.data?.favoriteVideos ?: return@onEach
                    val isLike = favoritesList.contains(state.videoSeed?.pk)
                    this.state.value = state.copy(isLike = isLike, isLoading = false)
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun shareVideo() {

    }

    private fun onChangeNumberLike(number: Int) {
        state.value?.let { state ->
            this.state.value = state.copy(numberLike = number)
        }
    }

    private fun onChangeNumberComment(number: Int) {
        state.value?.let { state ->
            this.state.value = state.copy(numberComments = number)
        }
    }

    private fun initSeedVideo(videoSeed: VideoSeed?) {
        state.value?.let { state ->
            this.state.value = state.copy(videoSeed = videoSeed)
        }
    }

    private fun checkFollowing() {
        state.value?.let { state ->
            val followingList = sessionManager.state.value?.profile?.following
            val authProfileId = state.videoSeed?.authProfileId
            if (followingList == null) {
                Log.d("TAG", "checkFollowing: favoritesList is empty")
                return
            }
            val isFollow = followingList.contains(authProfileId)
            this.state.value = state.copy(isFollow = isFollow)
        }
    }

    fun checkIsAuthVideo(): Boolean {
        state.value?.let { state ->
            return state.videoSeed?.authProfileId == sessionManager.state.value?.profile?.pk
        }
        return false
    }
}