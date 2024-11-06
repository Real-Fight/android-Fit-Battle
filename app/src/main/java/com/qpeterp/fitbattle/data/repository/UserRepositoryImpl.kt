package com.qpeterp.fitbattle.data.repository

import android.util.Log
import com.qpeterp.fitbattle.application.MyApplication
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.data.remote.service.UserService
import com.qpeterp.fitbattle.domain.model.battle.history.BattleHistory
import com.qpeterp.fitbattle.domain.model.user.User
import com.qpeterp.fitbattle.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {
    override suspend fun getUser(): User {
        val response = userService.getUser(MyApplication.prefs.token)

        if (response.isSuccessful) {
            Log.d(Constant.TAG, "get user data : ${response.body()}")
            return response.body()!!.data
        } else {
            throw Exception("Failed to fetch user data")
        }
    }

    override suspend fun patchProfileImage(image: String) {
        userService.updateProfile(
            authorization = MyApplication.prefs.token,
            image = image
        )
    }

    override suspend fun patchStatus(statusMessage: String) {
        userService.updateStatus(
            authorization = MyApplication.prefs.token,
            statusMessage = statusMessage
        )
    }

    override suspend fun getHistory(): List<BattleHistory> {
        val response = userService.getHistory(authorization = MyApplication.prefs.token)

        if (response.isSuccessful) {
            Log.d(Constant.TAG, "get history : ${response.body()}")
            return response.body()!!.data
        } else {
            throw Exception("Failed to fetch user data")
        }
    }
}