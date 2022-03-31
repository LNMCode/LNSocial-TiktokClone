package com.longnp.lnsocial.presentation.main.inbox.addfriend

import com.longnp.lnsocial.business.domain.models.inbox.Friend

sealed class AddFriendEvents {

    object GetFriendRecommend: AddFriendEvents()

    data class AddFriendMessage(val item: Friend): AddFriendEvents()
}