package com.longnp.lnsocial.presentation.main.discovery

import com.longnp.lnsocial.business.domain.models.ItemDiscovery
import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class DiscoveryState(
    val isLoading: Boolean = false,
    val items: List<ItemDiscovery> = listOf(),
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)