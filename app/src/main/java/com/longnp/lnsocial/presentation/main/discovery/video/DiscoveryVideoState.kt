package com.longnp.lnsocial.presentation.main.discovery.video

import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class DiscoveryVideoState(
    val isLoading: Boolean = false,
    val videoSeedList: List<VideoSeed> = listOf(),
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)