package com.qpeterp.fitbattle.domain.usecase.auth

import com.qpeterp.fitbattle.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(param: Param) = kotlin.runCatching {
        authRepository.register(
            loginId = param.loginId,
            password = param.password,
            name = param.name
        )
    }

    data class Param(
        val loginId: String,
        val password: String,
        val name: String
    )
}