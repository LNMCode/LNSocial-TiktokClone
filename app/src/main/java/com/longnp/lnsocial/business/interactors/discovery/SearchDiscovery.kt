package com.longnp.lnsocial.business.interactors.discovery

import com.longnp.lnsocial.business.domain.models.ItemDiscovery
import com.longnp.lnsocial.business.domain.util.DataState
import com.longnp.lnsocial.presentation.main.discovery.DiscoveryDataRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchDiscovery(
    private val discoveryDataRepo: DiscoveryDataRepo,
) {
    fun execute(): Flow<DataState<List<ItemDiscovery>>> = flow {

        emit(DataState.loading<List<ItemDiscovery>>())

        val data = discoveryDataRepo.getStoriesData()
        emit(
            DataState.data(
                response = null,
                data = data
            )
        )
    }
}