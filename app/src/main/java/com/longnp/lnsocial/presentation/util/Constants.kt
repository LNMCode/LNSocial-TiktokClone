package com.longnp.lnsocial.presentation.util

import com.bumptech.glide.request.RequestOptions
import com.longnp.lnsocial.R

class Constants {
    companion object {
        val requestOptions = RequestOptions
            .placeholderOf(R.drawable.default_image)
            .error(R.drawable.default_image)
    }
}