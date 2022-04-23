package com.longnp.lnsocial.presentation.main.create.select

import com.longnp.lnsocial.business.datasource.local.media.LocalImage
import com.longnp.lnsocial.business.datasource.local.media.LocalVideo
import com.longnp.lnsocial.business.datasource.local.media.MediaLocalInteraction
import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class SelectMediaState(
    val isLoading: Boolean = false,
    val mediaLocalInteraction: MediaLocalInteraction? = null,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)