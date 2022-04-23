package com.longnp.lnsocial.business.datasource.local.space

import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import com.andre_max.tiktokclone.repo.local.space.LocalSpaceRepo
import com.longnp.lnsocial.business.datasource.local.utils.toMB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DefaultLocalSpaceRepo
@Inject
constructor() : LocalSpaceRepo {

    companion object {
        const val MINIMUM_DEVICE_STORAGE_IN_MB = 100
    }

    @Suppress("DEPRECATION")
    override fun hasEnoughSpace(): Boolean {
        val rootDir: File = Environment.getExternalStorageDirectory()
        val stat = StatFs(rootDir.path)
        val sizeInBytes = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val blockSize = stat.blockSizeLong
            val availableBlocks = stat.availableBlocksLong
            blockSize * availableBlocks
        } else {
            val blockSize = stat.blockSize.toLong()
            val availableBlocks = stat.availableBlocks.toLong()
            blockSize * availableBlocks
        }
        val sizeInMB = sizeInBytes.toMB()
        return sizeInMB > MINIMUM_DEVICE_STORAGE_IN_MB
    }

    override suspend fun compressVideo(context: Context, originalFilePath: String) =
        withContext(Dispatchers.IO) {
            val videoDirectory = File(originalFilePath).parent
            val destPath = File(videoDirectory, generateFileName()).path

            compressMedium(originalFilePath, destPath)
            deleteOriginalFile(originalFilePath)

            return@withContext destPath
        }

    private fun deleteOriginalFile(originalFilePath: String) {
        try {
            val deleteWorked = File(originalFilePath).delete()
        } catch (e: Exception) {
        }
    }

    private fun generateFileName() =
        "VIDEO_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.mp4"

    @Suppress("INACCESSIBLE_TYPE")
    private suspend fun compressMedium(srcPath: String, destPath: String) =
        suspendCancellableCoroutine<String> { cont ->

        }
}