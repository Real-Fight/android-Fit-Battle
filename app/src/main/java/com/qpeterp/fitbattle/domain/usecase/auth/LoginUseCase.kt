package com.qpeterp.fitbattle.domain.usecase.auth

import com.qpeterp.fitbattle.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(param: Param): Result<Response<Unit>> {
        return runCatching {
            authRepository.login(param.loginId, param.password)
        }
    }

    data class Param(
        val loginId: String,
        val password: String
    )
}