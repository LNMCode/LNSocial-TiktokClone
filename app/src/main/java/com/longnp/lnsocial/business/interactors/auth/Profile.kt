package com.longnp.lnsocial.business.interactors.auth

import com.longnp.lnsocial.business.datasource.network.auth.OpenApiAuthService
import com.longnp.lnsocial.business.domain.models.Profile
import com.longnp.lnsocial.business.domain.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Profile(
    private val service: OpenApiAuthService,
) {
    fun execute(
        userid: String,
        accessToken: String,
    ): Flow<DataState<Profile>> = flow {

    }
}