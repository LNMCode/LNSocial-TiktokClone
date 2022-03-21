package com.longnp.lnsocial.business.interactors.video

import com.longnp.lnsocial.business.datasource.network.main.OpenApiMainService
import com.longnp.lnsocial.business.datasource.network.main.toVideo
import com.longnp.lnsocial.business.domain.models.AuthToken
import com.longnp.lnsocial.business.domain.models.VideoSeed
import com.longnp.lnsocial.business.domain.util.DataState
import com.longnp.lnsocial.business.domain.util.MessageType
import com.longnp.lnsocial.business.domain.util.Response
import com.longnp.lnsocial.business.domain.util.UIComponentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody

class SearchVideoSeeds(
    private val service: OpenApiMainService,
) {
    fun execute(
        body: RequestBody,
    ): Flow<DataState<List<VideoSeed>>> = flow{
        emit(DataState.loading<List<VideoSeed>>())
        try{ // catch network exception
            val blogs = service.getVideoSeeds(
                body
            ).map { it.toVideo() }

            // Insert into cache
            /*for(blog in blogs){
                try{
                    cache.insert(blog.toEntity())
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }*/
        }catch (e: Exception){
            emit(
                DataState.error<List<VideoSeed>>(
                    response = Response(
                        message = "Unable to update the cache.",
                        uiComponentType = UIComponentType.None(),
                        messageType = MessageType.Error()
                    )
                )
            )
        }
    }
}