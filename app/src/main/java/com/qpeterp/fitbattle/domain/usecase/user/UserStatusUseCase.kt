package com.qpeterp.fitbattle.domain.usecase.user

import com.qpeterp.fitbattle.domain.repository.UserRepository
import javax.inject.Inject

class UserStatusUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(param: Param): Result<Unit> {
        return kotlin.runCatching {
            userRepository.patchStatus(statusMessage = param.statusMessage)
        }
    }

    data class Param(
        val statusMessage: String
    )
}