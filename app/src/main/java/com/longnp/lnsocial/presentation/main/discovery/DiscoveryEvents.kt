package com.longnp.lnsocial.presentation.main.discovery

import com.longnp.lnsocial.business.domain.util.StateMessage

sealed class DiscoveryEvents {
    object NewSearch: DiscoveryEvents()

    data class Error(val stateMessage: StateMessage): DiscoveryEvents()

    object OnRemoveHeadFromQueue: DiscoveryEvents()
}