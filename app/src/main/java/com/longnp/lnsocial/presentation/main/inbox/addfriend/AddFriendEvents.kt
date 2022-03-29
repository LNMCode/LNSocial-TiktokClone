package com.longnp.lnsocial.presentation.main.inbox.addfriend

sealed class AddFriendEvents {

    object GetFriendRecommend: AddFriendEvents()

    data class AddFriend(val id: String): AddFriendEvents()
}