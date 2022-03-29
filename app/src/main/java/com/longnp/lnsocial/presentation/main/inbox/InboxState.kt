package com.longnp.lnsocial.presentation.main.inbox

import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class InboxState(
    val isLoading: Boolean = false,
    val inboxModelList: List<InboxModel> = listOf(),
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)