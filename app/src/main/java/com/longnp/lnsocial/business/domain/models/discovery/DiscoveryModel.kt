package com.longnp.lnsocial.business.domain.models.discovery

import android.os.Parcelable
import com.longnp.lnsocial.business.domain.models.VideoSeed
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiscoveryModel(
    val id: String,
    val title: String,
    val hashtagTitle: String,
    val data: List<VideoSeed>
): Parcelable