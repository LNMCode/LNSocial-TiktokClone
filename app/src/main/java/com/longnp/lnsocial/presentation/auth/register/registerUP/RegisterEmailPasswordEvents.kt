package com.longnp.lnsocial.presentation.auth.register.registerUP

sealed class RegisterEmailPasswordEvents {
    object Register: RegisterEmailPasswordEvents()

    data class OnUpdateUsername(val username: String): RegisterEmailPasswordEvents()

    data class OnUpdatePassword(val password: String): RegisterEmailPasswordEvents()

    data class OnUpdateConfirmPassword(val password: String): RegisterEmailPasswordEvents()
}