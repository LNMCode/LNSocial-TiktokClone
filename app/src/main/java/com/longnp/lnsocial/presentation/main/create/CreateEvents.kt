package com.longnp.lnsocial.presentation.main.create

import com.longnp.lnsocial.business.domain.util.StateMessage
import com.longnp.lnsocial.presentation.main.discovery.DiscoveryEvents

sealed class CreateEvents {

    object NewSearch: CreateEvents()

    object NewAdd: CreateEvents()

    data class Error(val stateMessage: StateMessage): CreateEvents()

    object OnRemoveHeadFromQueue: CreateEvents()

}