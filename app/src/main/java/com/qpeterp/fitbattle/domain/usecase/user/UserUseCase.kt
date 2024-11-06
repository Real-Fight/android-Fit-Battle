package com.qpeterp.fitbattle.domain.usecase.user

import com.qpeterp.fitbattle.domain.model.user.User
import com.qpeterp.fitbattle.domain.repository.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<User> {
        return kotlin.runCatching {
            userRepository.getUser()
        }
    }
}