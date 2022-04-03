package com.longnp.lnsocial.business.datasource.network.inbox.response

import com.google.gson.annotations.SerializedName
import com.longnp.lnsocial.business.domain.models.inbox.InboxModel

data class InboxModelDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("idAuth")
    val idAuth: String, // id profile user

    @SerializedName("idReceiver")
    val idReceiver: String,

    @SerializedName("nameReceiver")
    val nameReceiver: String,

    @SerializedName("avaReceiver")
    val avaReceiver: String,
)

fun InboxModelDto.toInboxModel(): InboxModel {
    return InboxModel(
        id = id,
        idAuth = idAuth, // nguoi so huu tin nhan
        idReceiver = idReceiver,
        nameReceiver = nameReceiver,
        avaReceiver = avaReceiver
    )
}