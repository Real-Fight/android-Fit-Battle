package com.qpeterp.fitbattle.presentation.features.battle.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.data.socket.WebSocketManager
import com.qpeterp.fitbattle.data.socket.data.EmptyData
import com.qpeterp.fitbattle.data.socket.data.MatchedData
import com.qpeterp.fitbattle.domain.model.battle.user.User
import com.qpeterp.fitbattle.presentation.features.battle.common.BattleConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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
                is EmptyData -> {
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

                    viewModelScope.launch {
                        matched()
                    }
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

    fun matchingCancel() {
        webSocketManager.sendMatchCancel()
    }

    private suspend fun matched() {
        setMatchingState()

        delay(2000)
        matchingState.value = true
    }
}