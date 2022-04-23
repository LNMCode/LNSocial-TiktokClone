package com.longnp.lnsocial.business.datasource.local.record

import android.content.Context

interface RecordVideoRepo {
    suspend fun initVideo(context: Context, timeCreated: Long): LocalRecordLocation?

    suspend fun stopVideo(context: Context)
}