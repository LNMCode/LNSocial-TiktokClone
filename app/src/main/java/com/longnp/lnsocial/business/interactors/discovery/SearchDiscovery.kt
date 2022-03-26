package com.longnp.lnsocial.business.interactors.discovery

import com.longnp.lnsocial.business.domain.models.discovery.DiscoveryModel
import com.longnp.lnsocial.business.domain.util.DataState
import com.longnp.lnsocial.presentation.main.discovery.DiscoveryDataRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchDiscovery(
    private val discoveryDataRepo: DiscoveryDataRepo,
) {
    fun execute(): Flow<DataState<List<DiscoveryModel>>> = flow {

        emit(DataState.loading<List<DiscoveryModel>>())

        val data = discoveryDataRepo.getStoriesData()
        emit(
            DataState.data(
                response = null,
                data = data
            )
        )
    }
}