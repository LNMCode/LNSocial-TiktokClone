package com.longnp.lnsocial.presentation.main.create.select

import android.content.Context
import com.longnp.lnsocial.business.domain.util.StateMessage

sealed class SelectMediaEvents {

    data class GetAllImageAndVideo(val context: Context): SelectMediaEvents()

    data class Error(val stateMessage: StateMessage): SelectMediaEvents()

    object onRemoveHeadFromQueue: SelectMediaEvents()
}