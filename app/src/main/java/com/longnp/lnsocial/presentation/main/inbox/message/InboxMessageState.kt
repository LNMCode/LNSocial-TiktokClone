package com.longnp.lnsocial.presentation.main.inbox.message

import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
import com.longnp.lnsocial.business.domain.models.inbox.Message
import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class InboxMessageState(
    val isLoading: Boolean = false,
    val inboxModel: InboxModel? = null,
    val messages: List<Message> = listOf(),
    val valueMessage: String = "",
    val positionLastMessage: Int = 0,
    val queue: Queue<StateMessage> = Queue(mutableListOf())
)