package com.longnp.lnsocial.presentation.auth.login

import com.longnp.lnsocial.business.domain.util.Queue
import com.longnp.lnsocial.business.domain.util.StateMessage

data class LoginState(
    val isLoading: Boolean = false,
    val username: String = "",
    val password: String = "",
    val queue: Queue<StateMessage> = Queue(mutableListOf())
)