package com.longnp.lnsocial.presentation.main.create.record

import com.longnp.lnsocial.business.domain.util.StateMessage

sealed class CreateEvents {

    object NewSearch: CreateEvents()

    object NewAdd: CreateEvents()

    data class Error(val stateMessage: StateMessage): CreateEvents()

    object OnRemoveHeadFromQueue: CreateEvents()

}