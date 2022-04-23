package com.longnp.lnsocial.business.datasource.local.media

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri

interface LocalMediaRepo {
    // TODO: Remove this
    @SuppressLint("NewApi")
    suspend fun getAllImages(context: Context)

    // TODO: Remove this
    @SuppressLint("NewApi")
    suspend fun getAllVideos(context: Context)

    suspend fun getVideoDuration(context: Context, fileUri: Uri?): Long

    @Suppress("DEPRECATION")
    fun getFilePathFromCursor(cursor: Cursor): String?
}