package com.longnp.lnsocial.business.datasource.network.main.response

import com.google.gson.annotations.SerializedName
import com.longnp.lnsocial.business.datasource.network.main.CommentDto

class SeedItemCommentsResponse(

    @SerializedName("comments")
    val comments: List<CommentDto>

)