package com.longnp.lnsocial.business.domain.models.inbox

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Message(
    val id: String = "", // id of user send message
    val value: String = "",
    val date: Int = 0,
    var type: Int = 0, // type is receiver or sender
    var ava: String = "",
) : Parcelable

// Convert to map to send firebase
fun Message.toMap(): Map<String, Any?> {
    return mapOf(
        "id" to id,
        "value" to value,
        "date" to date,
        "type" to type,
        "ava" to ava
    )
}