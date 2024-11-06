package com.qpeterp.fitbattle.domain.model.battle.history

import kotlinx.serialization.Serializable

@Serializable
data class BattleHistory(
    val matchType: String,
    val result: String,
    val score: Int
)
