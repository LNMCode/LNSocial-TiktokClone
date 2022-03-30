package com.longnp.lnsocial.presentation.main.inbox.message

sealed class InboxMessageEvents {

    data class Send(val title: String) : InboxMessageEvents()

    data class GetMessage(val id: String) : InboxMessageEvents()

}