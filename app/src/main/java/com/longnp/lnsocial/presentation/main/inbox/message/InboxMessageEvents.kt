package com.longnp.lnsocial.presentation.main.inbox.message

import com.longnp.lnsocial.business.domain.models.inbox.InboxModel

sealed class InboxMessageEvents {

    data class Send(val title: String) : InboxMessageEvents()

    data class GetMessage(val id: String) : InboxMessageEvents()

    data class OnUpdateInboxModel(val inboxModel: InboxModel): InboxMessageEvents()
}