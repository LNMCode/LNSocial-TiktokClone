package com.longnp.lnsocial.presentation.main.inbox.message

import com.longnp.lnsocial.business.domain.models.inbox.InboxModel
import com.longnp.lnsocial.business.domain.models.inbox.Message

sealed class InboxMessageEvents {

    object Send : InboxMessageEvents()

    data class GetMessage(val id: String) : InboxMessageEvents()

    data class OnUpdateInboxModel(val inboxModel: InboxModel): InboxMessageEvents()

    data class OnUpdateMessages(val messages: List<Message>?): InboxMessageEvents()

    data class OnUpdateValueMessage(val message: String): InboxMessageEvents()

    object OnSuccessSend: InboxMessageEvents()

    data class OnScrollToLastMessage(val position: Int): InboxMessageEvents()
}