package com.longnp.lnsocial.presentation.main.seeds.list

import com.longnp.lnsocial.business.domain.util.StateMessage

sealed class SeedEvents {

    object NewSearch : SeedEvents()

    object NextPage: SeedEvents()

    data class UpdateQuery(val query: String): SeedEvents()

    data class Error(val stateMessage: StateMessage): SeedEvents()

    object OnRemoveHeadFromQueue: SeedEvents()

}