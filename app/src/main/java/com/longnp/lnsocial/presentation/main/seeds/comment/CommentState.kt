package com.longnp.lnsocial.presentation.main.seeds.comment

import com.longnp.lnsocial.business.domain.models.Comment
import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class CommentState(
    val isLoading: Boolean = false,
    val videoId: String? = null,
    val comments: List<Comment> = listOf(),
    val comment: String? = null,
    val queue: Queue<StateMessage> = Queue(mutableListOf())
)