package com.longnp.lnsocial.business.domain.models

data class AuthToken(
    val accountPk: String,
    val token: String,
    val authProfileId: String,
)