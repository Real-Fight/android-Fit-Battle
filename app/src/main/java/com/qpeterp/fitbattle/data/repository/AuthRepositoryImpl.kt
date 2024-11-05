package com.qpeterp.fitbattle.data.repository

import com.qpeterp.fitbattle.data.data.LoginData
import com.qpeterp.fitbattle.data.data.RegisterData
import com.qpeterp.fitbattle.data.remote.service.AuthService
import com.qpeterp.fitbattle.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun login(
        loginId: String,
        password: String
    ): Response<Unit> {
        return authService.login(
            LoginData(
                loginId = loginId,
                password = password
            )
        )
    }

    override suspend fun register(
        loginId: String,
        password: String,
        name: String
    ): Response<Unit> {
        return authService.register(
            RegisterData(
                loginId = loginId,
                password = password,
                name = name
            )
        )
    }
}