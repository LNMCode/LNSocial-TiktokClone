package com.longnp.lnsocial.business.datasource.network.main

import com.google.gson.annotations.SerializedName
import com.longnp.lnsocial.business.domain.models.SeedItem

class SeedItemDto(

    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    var message: String,
)

fun SeedItemDto.toSeedItem(): SeedItem {
    return SeedItem(
        status = status,
        message = message,
    )
}