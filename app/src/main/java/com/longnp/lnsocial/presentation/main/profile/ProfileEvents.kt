package com.longnp.lnsocial.presentation.main.profile

import com.longnp.lnsocial.business.domain.util.StateMessage

sealed class ProfileEvents {
    object GetProfile: ProfileEvents()

    data class Error(val stateMessage: StateMessage): ProfileEvents()

    data class GetVideoByType(val type: String): ProfileEvents()

    object OnRemoveHeadFromQueue: ProfileEvents()
}