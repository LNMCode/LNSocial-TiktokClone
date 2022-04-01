package com.longnp.lnsocial.presentation.auth.login

sealed class LoginEvents {

    object Login: LoginEvents()

    data class OnUpdateUsername(val username: String): LoginEvents()

    data class OnUpdatePassword(val password: String): LoginEvents()
}