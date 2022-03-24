package com.longnp.lnsocial.presentation.main.profile

import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class ProfileState(
    val isLoading: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf())
)