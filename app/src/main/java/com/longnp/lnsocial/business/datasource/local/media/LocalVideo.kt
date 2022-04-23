package com.longnp.lnsocial.business.datasource.local.media

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocalVideo(
    var filePath: String?,
    val duration: Long?,
    val dateCreated: String?
): Parcelable