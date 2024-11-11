package com.qpeterp.fitbattle.domain.model.user

import com.qpeterp.fitbattle.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class User(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val profileImgUrl: String,
    val statusMessage: String,
    val strength: Int,
    val endurance: Int,
    val agility: Int,
    val willpower: Int,
    val totalPower: Int,
    val ranking: Int,
)
