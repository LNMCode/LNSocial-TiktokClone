package com.longnp.lnsocial.presentation.main.inbox.addfriend

import com.longnp.lnsocial.business.domain.models.inbox.Friend
import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class AddFriendState(
    val isLoading: Boolean = false,
    val friends: List<Friend> = listOf(),
    val inboxModel: InboxModel? = null,
    val isAddFriendComplete: Boolean = false,
    val queue: Queue<StateMessage> = Queue(mutableListOf())
)