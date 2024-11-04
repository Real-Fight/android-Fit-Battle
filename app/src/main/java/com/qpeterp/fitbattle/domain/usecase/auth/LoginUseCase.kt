package com.qpeterp.fitbattle.domain.usecase.auth

import com.qpeterp.fitbattle.domain.repository.auth.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(param: Param) = kotlin.runCatching {
        authRepository.login(
            loginId = param.loginId,
            password = param.password
        )
    }

    data class Param(
        val loginId: String,
        val password: String
    )
}