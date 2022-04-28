package com.longnp.lnsocial.business.interactors.auth

import android.util.Log
import com.longnp.lnsocial.business.datasource.cache.profile.ProfileDao
import com.longnp.lnsocial.business.datasource.cache.profile.toProfile
import com.longnp.lnsocial.business.domain.models.Profile
import com.longnp.lnsocial.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UpdateUsernameFromCache(
    private val profileDao: ProfileDao,
) {
    fun execute(
        pk: String,
        username: String,
    ): Flow<DataState<Profile>> = flow{
        emit(DataState.loading<Profile>())
        profileDao.updateUsername(pk = pk, username = username)
        val profile = profileDao.searchByPk(pk = pk)?.toProfile()
        emit(DataState.data(response = null, data = profile))
    }.catch { e ->
        Log.d("TAG", "execute get from cache: " + e.message)
    }
}