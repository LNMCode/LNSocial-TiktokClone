package com.longnp.lnsocial.business.domain.models

data class AuthToken(
    val accountPk: Int,
    val token: String,
)