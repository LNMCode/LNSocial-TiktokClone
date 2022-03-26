package com.longnp.lnsocial.business.datasource.network.discovery.response

import com.longnp.lnsocial.business.domain.models.discovery.DiscoveryModel

// This is temple class for review data in file json.
// Maybe get data from server in future
data class ResponseDiscoveryModel(
    var data: List<DiscoveryModel>
)