package com.longnp.lnsocial.business.domain.models.inbox

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    val id: String,
    val value: String,
    val date: String,
    val type: Int, // type is receiver or sender
): Parcelable