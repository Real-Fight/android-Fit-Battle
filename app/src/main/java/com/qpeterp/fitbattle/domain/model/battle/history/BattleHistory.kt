package com.qpeterp.fitbattle.domain.model.battle.history

import com.qpeterp.fitbattle.domain.model.battle.type.MatchType
import kotlinx.serialization.Serializable

@Serializable
data class BattleHistory(
    val matchType: MatchType,
    val result: String,
    val score: Int
)
