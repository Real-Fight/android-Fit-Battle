package com.qpeterp.fitbattle.domain.usecase.user

import com.qpeterp.fitbattle.domain.repository.UserRepository
import javax.inject.Inject

class UserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(param: Param): Result<Unit> {
        return kotlin.runCatching {
            userRepository.patchProfileImage(image = param.image)
        }
    }

    data class Param(
        val image: String
    )
}