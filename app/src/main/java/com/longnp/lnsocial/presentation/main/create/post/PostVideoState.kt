package com.longnp.lnsocial.presentation.main.create.post

import com.longnp.lnsocial.business.datasource.local.media.LocalVideo
import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class PostVideoState(
    val isLoading: Boolean = false,
    val localVideo: LocalVideo? = null,
    val isSuccess: Boolean = false,
    val description: String = "This is video from android",
    val hashTags: String = "",
    val queue: Queue<StateMessage> = Queue(mutableListOf())
)