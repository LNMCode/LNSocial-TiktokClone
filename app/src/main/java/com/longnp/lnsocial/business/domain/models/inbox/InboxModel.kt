package com.longnp.lnsocial.business.domain.models.inbox

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InboxModel(
    val id: String,
    val accessToken: String,
    val idAuth: String, // id profile user
    val idReceiver: String,
    val nameReceiver: String,
    val avaReceiver: String,
    val messages: List<Message>,
    val lastMessage: String,
    val lastUpdated: String,
): Parcelable