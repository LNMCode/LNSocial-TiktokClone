package com.longnp.lnsocial.business.interactors.post

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import com.longnp.lnsocial.business.datasource.local.media.LocalVideo
import com.longnp.lnsocial.business.datasource.local.space.DefaultLocalSpaceRepo
import com.longnp.lnsocial.business.datasource.network.create.toPostVideo
import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.datasource.network.main.toVideo
import com.longnp.lnsocial.business.domain.models.AuthToken
import com.longnp.lnsocial.business.domain.models.PostVideo
import com.longnp.lnsocial.business.domain.util.Constants
import com.longnp.lnsocial.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PostVideo(
    private val service: OpenApiMainService,
    private val localSpaceRepo: DefaultLocalSpaceRepo,
) {
    fun execute(
        context: Context,
        localVideo: LocalVideo,
        authToken: AuthToken?,
        idUser: String,
        description: String,
    ): Flow<DataState<PostVideo>> = flow {
        emit(DataState.loading())
        if (authToken == null) {
            throw Exception("SearchVideoSeeds: Auth token is null")
        }
        val videoFile = File(localVideo.filePath ?: return@flow)
        val videoBody: RequestBody = RequestBody.create(MediaType.parse("video/*"), videoFile)
        val vFile = MultipartBody.Part.createFormData("video", videoFile.name, videoBody)

        val data = service.postVideo(
            video = vFile,
            accessToken = authToken.token,
            idUser = idUser,
            type = "Space",
            hashTagsVideo = "",
            soundId = "",
            authProfileId = authToken.authProfileId,
            numberLike = 0,
            numberComment = 0,
            numberShare = 0,
            description = description
        )
        emit(DataState.data(response = null, data = data.toPostVideo()))
    }.catch { e ->
        Log.d("TAG", "PostVideo " + e.message)

    }
}