package com.longnp.lnsocial.business.interactors.video

import android.util.Log
import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.datasource.network.main.toComment
import com.longnp.lnsocial.business.domain.models.AuthToken
import com.longnp.lnsocial.business.domain.models.Comment
import com.longnp.lnsocial.business.domain.util.Constants
import com.longnp.lnsocial.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AddCommentVideo(
    private val service: OpenApiMainService,
) {
    fun execute(
        pk: String, // id video
        comment: String,
        authToken: AuthToken,
    ): Flow<DataState<List<Comment>>> = flow {
        emit(DataState.loading<List<Comment>>())
        val paramsRequestBody = Constants.getParamsBodyAuth(
            hashMapOf(
                "userid" to authToken.accountPk,
                "access_token" to authToken.token,
                "auth_profile_id" to authToken.authProfileId,
                "videoid" to pk,
                "comment" to comment,
            )
        )
        val bodyRequest = Constants.getRequestBodyAuth(paramsRequestBody.toString())
        val data = service.commentVideo(bodyRequest)
        emit(DataState.data(response = null, data = data.map { it.toComment() }))
    }.catch { e ->
        Log.d("TAG", "execute: ${e.message}")
    }}