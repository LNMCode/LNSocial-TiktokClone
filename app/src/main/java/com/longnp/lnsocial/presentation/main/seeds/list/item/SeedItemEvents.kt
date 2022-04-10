package com.longnp.lnsocial.presentation.main.seeds.list.item

import com.longnp.lnsocial.business.domain.models.VideoSeed

sealed class SeedItemEvents {

    object LikeVideo : SeedItemEvents()

    object Follow: SeedItemEvents()

    object GetCommentVideo: SeedItemEvents()

    data class Comment(val comment: String): SeedItemEvents()

    object ShareVideo: SeedItemEvents()

    data class InitSeedVideo(val videoSeed: VideoSeed?): SeedItemEvents()

    data class OnChangeNumberLike(val number: Int): SeedItemEvents()

    data class OnChangeNumberComment(val number: Int): SeedItemEvents()

    object OnChangeIsLike: SeedItemEvents()

    object CheckFollowing: SeedItemEvents()

}