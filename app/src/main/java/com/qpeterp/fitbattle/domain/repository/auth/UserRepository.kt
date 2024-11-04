package com.qpeterp.fitbattle.domain.repository.auth

interface AuthRepository {
    suspend fun login(
        loginId: String,
        password: String
    )

    suspend fun register(
        loginId: String,
        password: String,
        name: String
    )
}