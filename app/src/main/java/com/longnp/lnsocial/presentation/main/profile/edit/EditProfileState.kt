package com.longnp.lnsocial.presentation.main.profile.edit

import com.longnp.lnsocial.business.domain.models.Profile

data class EditProfileState(
    val isLoading: Boolean = false,
    val isUpdateUI: Boolean = false,
    val profile: Profile? = null,
    val username: String? = null
)