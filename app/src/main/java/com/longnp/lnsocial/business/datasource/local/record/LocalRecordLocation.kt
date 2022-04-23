package com.longnp.lnsocial.business.datasource.local.record

import android.net.Uri
import java.io.FileDescriptor

data class LocalRecordLocation(val contentUri: Uri, val filePath: String, val fileDescriptor: FileDescriptor)