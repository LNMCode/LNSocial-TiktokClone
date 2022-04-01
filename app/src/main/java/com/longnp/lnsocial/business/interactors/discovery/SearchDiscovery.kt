package com.longnp.lnsocial.business.interactors.discovery

import androidx.annotation.RawRes
import com.longnp.lnsocial.business.domain.models.discovery.DiscoveryModel
import com.longnp.lnsocial.business.domain.util.DataState
import com.longnp.lnsocial.presentation.main.discovery.DiscoveryDataRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class SearchDiscovery(
    private val dataRepoJson: DiscoveryDataRepo,
) {
    fun execute(
        @RawRes id: Int,
    ): Flow<DataState<List<DiscoveryModel>>> = flow {

        emit(DataState.loading<List<DiscoveryModel>>())

        val data = dataRepoJson.getStoriesData(id = id)
        emit(
            DataState.data(
                response = null,
                data = data
            )
        )
    }.catch {

    }
}