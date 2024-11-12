package com.qpeterp.fitbattle.domain.usecase.user

import com.qpeterp.fitbattle.domain.model.user.Quest
import com.qpeterp.fitbattle.domain.repository.UserRepository
import javax.inject.Inject

class QuestUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<Quest> {
        return kotlin.runCatching {
            userRepository.getQuest()
        }
    }
}