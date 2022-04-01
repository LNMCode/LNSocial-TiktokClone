package com.longnp.lnsocial.presentation.auth.register

sealed class RegisterEvents {

    data class OnUpdateUsername(val username: String): RegisterEvents()

    data class OnUpdatePassword(val password: String): RegisterEvents()

    data class OnUpdateConfirmPassword(val password: String): RegisterEvents()

    object Register: RegisterEvents()
}