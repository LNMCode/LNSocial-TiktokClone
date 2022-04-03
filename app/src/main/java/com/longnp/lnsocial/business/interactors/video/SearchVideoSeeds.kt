package com.longnp.lnsocial.business.interactors.video

import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.datasource.network.main.VideoSeedDto
import com.longnp.lnsocial.business.datasource.network.main.toVideo
import com.longnp.lnsocial.business.domain.models.AuthToken
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.business.domain.util.*
import com.longnp.lnsocial.business.domain.util.Constants.Companion.getParamsBodyAuth
import com.longnp.lnsocial.business.domain.util.Constants.Companion.getRequestBodyAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import java.lang.Exception

class SearchVideoSeeds(
    private val service: OpenApiMainService,
) {
    fun execute(
        authToken: AuthToken?,
    ): Flow<DataState<List<VideoSeed>>> = flow{
        emit(DataState.loading<List<VideoSeed>>())
        if (authToken == null) {
            throw Exception("SearchVideoSeeds: Auth token is null")
        }
        val paramsRequestBody = getParamsBodyAuth(
            hashMapOf(
                "userid" to authToken.accountPk,
                "access_token" to authToken.token,
                "auth_profile_id" to authToken.authProfileId
            )
        )
        val bodyRequest = getRequestBodyAuth(paramsRequestBody.toString())
        val blogs = service.getVideoSeeds(bodyRequest).data

        // Insert into cache
        /*for(blog in blogs){
            try{
                cache.insert(blog.toEntity())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }*/
        emit(DataState.data(response = null, data =  blogs.map { it.toVideo() }))
    }.catch { e->
        emit(
            DataState.error<List<VideoSeed>>(
                response = Response(
                    message = e.message,
                    uiComponentType = UIComponentType.None(),
                    messageType = MessageType.Error()
                )
            )
        )
    }
}