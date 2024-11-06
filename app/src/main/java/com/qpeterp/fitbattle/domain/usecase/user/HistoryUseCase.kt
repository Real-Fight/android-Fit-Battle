package com.qpeterp.fitbattle.domain.usecase.user

import com.qpeterp.fitbattle.domain.model.battle.history.BattleHistory
import com.qpeterp.fitbattle.domain.repository.UserRepository
import javax.inject.Inject

class HistoryUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<BattleHistory>> {
        return kotlin.runCatching {
            userRepository.getHistory()
        }
    }
}