package com.longnp.lnsocial.business.interactors.auth

import android.util.Log
import com.longnp.lnsocial.business.datasource.cache.profile.ProfileDao
import com.longnp.lnsocial.business.datasource.cache.profile.ProfileEntity
import com.longnp.lnsocial.business.datasource.cache.profile.toProfile
import com.longnp.lnsocial.business.domain.models.Profile
import com.longnp.lnsocial.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class ProfileFromCache(
    private val profile: ProfileDao,
) {
    fun execute(
        pk: String,
    ): Flow<DataState<Profile>> = flow{
        emit(DataState.loading<Profile>())
        val profile = profile.searchByPk(pk = pk)?.toProfile()
        emit(DataState.data(response = null, data = profile))
    }.catch { e ->
        Log.d("TAG", "execute get from cache: " + e.message)
    }
}