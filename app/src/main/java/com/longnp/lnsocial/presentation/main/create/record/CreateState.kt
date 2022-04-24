package com.longnp.lnsocial.presentation.main.create.record

import com.longnp.lnsocial.business.datasource.local.media.LocalVideo
import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class CreateState(
    val isLoading: Boolean = false,
    val hasRecordingStarted: Boolean = false,
    val isRecording: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)