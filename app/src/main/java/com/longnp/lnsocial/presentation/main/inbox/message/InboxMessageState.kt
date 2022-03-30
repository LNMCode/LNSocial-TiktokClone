package com.longnp.lnsocial.presentation.main.inbox.message

import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class InboxMessageState(
    val isLoading: Boolean = false,
    val message: InboxModel? = null,
    val queue: Queue<StateMessage> = Queue(mutableListOf())
)