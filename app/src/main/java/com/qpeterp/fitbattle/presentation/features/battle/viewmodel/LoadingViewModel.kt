package com.qpeterp.fitbattle.presentation.features.battle.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.data.socket.WebSocketManager
import com.qpeterp.fitbattle.data.socket.data.MatchOkData
import com.qpeterp.fitbattle.data.socket.data.MatchedData
import com.qpeterp.fitbattle.domain.model.battle.user.User
import com.qpeterp.fitbattle.presentation.features.battle.common.BattleConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val webSocketManager: WebSocketManager
) : ViewModel() {
    init {
        webSocketManager.start()

        Log.d(Constant.TAG, "matchType is ${BattleConstants.MATCH_TYPE}")
        webSocketManager.sendRandomMatch(
            matchType = BattleConstants.MATCH_TYPE
        )

        webSocketManager.setOnMessageReceived { message ->
            when (message) {
                is MatchOkData -> {
                    Log.d(Constant.TAG, "webSocketManager matchOk")
                }

                is MatchedData -> {
                    BattleConstants.ROOM_ID = message.roomId
                    BattleConstants.RIVAL_STATUS = User(
                        id = message.rivalId,
                        name = message.rivalName,
                        profileUrl = message.rivalProfileImgUrl,
                        ranking = message.rivalRanking.toString()
                    )
                    BattleConstants.USER_STATUS = User(
                        id = message.userId,
                        name = message.userName,
                        profileUrl = message.userProfileImgUrl,
                        ranking = message.userRanking.toString()
                    )
                    matchingState.value = true
                }

                else -> {
                    Log.d(Constant.TAG, "LoadingViewModel ㅈㅈㅈㅈㅈㅈㅈㅈㅈㅈㅈㅈ $message")
                }
            }
        }
    }

    val matchingState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun setMatchingState() {
        matchingState.value = !matchingState.value
    }

    fun readiedGame() {
        Log.d(Constant.TAG, "room id : ${BattleConstants.ROOM_ID}")
        webSocketManager.sendReadied(
            roomId = BattleConstants.ROOM_ID
        )
    }

    fun matchingCancel() {
        webSocketManager.sendMatchCancel()
    }
}