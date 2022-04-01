package com.longnp.lnsocial.business.interactors.video

import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.datasource.network.main.VideoSeedDto
import com.longnp.lnsocial.business.datasource.network.main.toVideo
import com.longnp.lnsocial.business.domain.models.AuthToken
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.business.domain.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody

class SearchVideoSeeds(
    private val service: OpenApiMainService,
) {
    fun execute(): Flow<DataState<List<VideoSeed>>> = flow{
        emit(DataState.loading<List<VideoSeed>>())
        val paramsRequestBody = Constants.PARAMS_RERQUEST_BODY
        val bodyRequest = Constants.getRequestBodyAuth(paramsRequestBody.toString())
        val blogs = service.getVideoSeeds(
            bodyRequest
        ).data

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