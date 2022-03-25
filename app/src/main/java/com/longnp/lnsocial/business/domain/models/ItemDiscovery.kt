package com.longnp.lnsocial.business.domain.models


data class ItemDiscovery(
    val title: String,
    val hashtagTitle: String,
    val data: List<ItemThumbnailDiscovery>,
)