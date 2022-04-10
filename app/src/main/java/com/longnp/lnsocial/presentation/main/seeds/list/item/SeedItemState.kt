package com.longnp.lnsocial.presentation.main.seeds.list.item

import com.longnp.lnsocial.business.domain.models.Comment
import com.longnp.lnsocial.business.domain.models.SeedItem
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class SeedItemState(
    val isLoading: Boolean = false,
    val videoSeed: VideoSeed? = null,
    val numberLike: Int = 0,
    val numberComments: Int = 0,
    val comments: List<Comment> = listOf(),
    val isLike: Boolean = true,
    val isFollow: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf())
)
