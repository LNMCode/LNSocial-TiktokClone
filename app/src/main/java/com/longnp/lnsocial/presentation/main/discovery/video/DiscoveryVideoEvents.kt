package com.longnp.lnsocial.presentation.main.discovery.video

import com.longnp.lnsocial.business.domain.models.VideoSeed

sealed class DiscoveryVideoEvents {

    data class GetVideo(val data: List<VideoSeed>): DiscoveryVideoEvents()
}