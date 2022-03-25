package com.longnp.lnsocial.presentation.main.inbox

import com.longnp.lnsocial.business.domain.util.StateMessage

sealed class InboxEvents {

    object  NewSearch: InboxEvents()

    data class Error(val stateMessage: StateMessage): InboxEvents()

    object OnRemoveHeadFromQueue: InboxEvents()
}