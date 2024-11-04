package com.qpeterp.fitbattle.domain.model.user

import java.util.UUID


data class User(
    val id: UUID,
    val loginId: String,
    val password: String,
    val name: String,
)
