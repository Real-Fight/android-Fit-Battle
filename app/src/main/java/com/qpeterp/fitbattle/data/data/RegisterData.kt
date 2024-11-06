package com.qpeterp.fitbattle.data.data

import kotlinx.serialization.Serializable

@Serializable
data class RegisterData(
    val loginId: String,
    val password: String,
    val name: String
)