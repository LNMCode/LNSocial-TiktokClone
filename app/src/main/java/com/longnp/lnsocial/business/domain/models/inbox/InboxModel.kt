package com.longnp.lnsocial.business.domain.models.inbox

data class InboxModel(
    val id: String,
    val accessToken: String,
    val idAuth: String,
    val idReceiver: String,
    val nameReceiver: String,
    val avaReceiver: String,
    val messages: List<Message>,
    val lastMessage: String,
    val lastUpdated: String,
)