package com.qpeterp.fitbattle.domain.repository

import com.qpeterp.fitbattle.domain.model.battle.history.BattleHistory
import com.qpeterp.fitbattle.domain.model.user.User

interface UserRepository {
    suspend fun getUser(): User

    suspend fun patchProfileImage(
        image: String
    )

    suspend fun patchStatus(
        statusMessage: String
    )

    suspend fun getHistory(): List<BattleHistory>

    suspend fun getQuest(): Quest
}