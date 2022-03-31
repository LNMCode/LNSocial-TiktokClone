package com.longnp.lnsocial.presentation.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.longnp.lnsocial.R

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