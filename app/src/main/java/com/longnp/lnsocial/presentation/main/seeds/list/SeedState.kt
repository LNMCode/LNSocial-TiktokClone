package com.longnp.lnsocial.presentation.main.seeds.list

import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class SeedState(
    val isLoading: Boolean = false,
    val videoSeedList: List<VideoSeed> = listOf(),
    val query: String = "",
    val page: Int = 1,
    val isQueryExhausted: Boolean = false, // no more results available, prevent next page
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)
