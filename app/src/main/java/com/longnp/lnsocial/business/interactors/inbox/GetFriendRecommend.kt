package com.longnp.lnsocial.business.interactors.inbox

import android.util.Log
import androidx.annotation.RawRes
import com.longnp.lnsocial.business.domain.models.inbox.Friend
import com.longnp.lnsocial.business.domain.util.DataState
import com.longnp.lnsocial.presentation.main.inbox.addfriend.FriendDataRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetFriendRecommend(
    private val dataRepoJson: FriendDataRepo
) {
    fun execute(
        @RawRes id: Int,
    ): Flow<DataState<List<Friend>>> = flow {
        emit(DataState.loading())
        try {
            val data = dataRepoJson.getStoriesData(id)
            emit(DataState.data(response = null, data = data))
        } catch (e: Exception) {
            Log.d("TAG", "GetFriendBug " + e.message)
        }
    }
}