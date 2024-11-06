package com.qpeterp.fitbattle.domain.repository

import retrofit2.Response

interface AuthRepository {
    suspend fun login(
        loginId: String,
        password: String
    ): Response<Unit>

    suspend fun register(
        loginId: String,
        password: String,
        name: String
    ): Response<Unit>
}