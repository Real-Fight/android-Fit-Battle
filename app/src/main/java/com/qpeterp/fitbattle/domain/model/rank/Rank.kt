package com.qpeterp.fitbattle.domain.model.rank

import com.qpeterp.fitbattle.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

// Domain 에 @Serializable 한번만..
@Serializable
data class Rank(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val name: String,
    val profileImgUrl: String,
    val ranking: Int,
    val statusMessage: String,
    val totalPower: Int
)
