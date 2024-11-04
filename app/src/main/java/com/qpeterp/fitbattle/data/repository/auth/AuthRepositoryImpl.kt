package com.qpeterp.fitbattle.data.repository.auth

import com.qpeterp.fitbattle.data.data.RegisterData
import com.qpeterp.fitbattle.data.remote.service.auth.AuthService
import com.qpeterp.fitbattle.domain.repository.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun login(
        loginId: String,
        password: String
    ) {
        authService.login()
    }

    override suspend fun register(
        loginId: String,
        password: String,
        name: String
    ) {
        authService.register(
            RegisterData(
                loginId = loginId,
                password = password,
                name = name
            )
        )
    }
}