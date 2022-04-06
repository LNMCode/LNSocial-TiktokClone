package com.longnp.lnsocial.presentation.main.profile

import com.longnp.lnsocial.business.domain.models.Profile
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class ProfileState(
    val isLoading: Boolean = false,
    val profile: Profile? = null,
    val videoPublic: List<VideoSeed> = listOf(),
    val videoFavorite: List<VideoSeed> = listOf(),
    val videoPrivate: List<VideoSeed> = listOf(),
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)