package com.longnp.lnsocial.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.longnp.lnsocial.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun ShapeableImageView.loadCenterCropImageFromUrl(
    imageUrl: String?,
    requestOptions: RequestOptions = Constants.requestOptions
) {
    Glide.with(this)
        .setDefaultRequestOptions(requestOptions)
        .load(imageUrl)
        .centerCrop()
        .into(this)
}

fun ShapeableImageView.loadImageFromUrl(
    imageUrl: String?,
    requestOptions: RequestOptions = Constants.requestOptions
) {
    Glide.with(this)
        .setDefaultRequestOptions(requestOptions)
        .load(imageUrl)
        .into(this)
}

fun ImageView.loadImageFromUrl(
    imageUrl: String?,
    requestOptions: RequestOptions = Constants.requestOptions
) {
    Glide.with(this)
        .setDefaultRequestOptions(requestOptions)
        .load(imageUrl)
        .into(this)
}

fun ImageView.loadCenterCropImageFromUrl(
    imageUrl: String?,
    requestOptions: RequestOptions = Constants.requestOptions
) {
    Glide.with(this)
        .setDefaultRequestOptions(requestOptions)
        .load(imageUrl)
        .centerCrop()
        .into(this)
}

fun ImageView.loadImageFromResource(
    image: Int,
) {
    Glide.with(this)
        .load(image)
        .into(this)
}

// Load image thumbnail from video
fun ShapeableImageView.loadThumbnailImageFromVideoPath(
    context: Context,
    filePath: String?,
    requestOptions: RequestOptions = Constants.requestOptions
) {
    var bitmap: Bitmap? = null
    try {
        val mediaMetadataRetriever = MediaMetadataRetriever().apply {
            setDataSource(context, Uri.parse(filePath))
        }
        bitmap = mediaMetadataRetriever.frameAtTime
        mediaMetadataRetriever.release()

    }catch (e: Exception) {
        Log.d("TAG", "loadThumbnailImageFromVideoPath: ${e.message}")
    }
    Glide.with(this)
        .setDefaultRequestOptions(requestOptions)
        .load(bitmap)
        .error(R.drawable.coding_image)
        .centerCrop()
        .into(this)
}