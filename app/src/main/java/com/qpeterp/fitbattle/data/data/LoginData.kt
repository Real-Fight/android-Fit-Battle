package com.qpeterp.fitbattle.data.data

import kotlinx.serialization.Serializable

@Serializable
data class LoginData(
    val loginId: String,
    val password: String
)
