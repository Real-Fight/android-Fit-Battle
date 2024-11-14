package com.qpeterp.fitbattle.presentation.features.battle.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.data.socket.WebSocketManager
import com.qpeterp.fitbattle.data.socket.data.EndGameData
import com.qpeterp.fitbattle.data.socket.data.MatchType
import com.qpeterp.fitbattle.data.socket.data.ScoreUpdateData
import com.qpeterp.fitbattle.data.socket.data.StartGameData
import com.qpeterp.fitbattle.domain.model.battle.result.GameResult
import com.qpeterp.fitbattle.domain.model.battle.user.User
import com.qpeterp.fitbattle.domain.model.pose.PoseType
import com.qpeterp.fitbattle.presentation.features.battle.common.BattleConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MuscleBattleViewModel @Inject constructor(
    private val webSocketManager: WebSocketManager
) : ViewModel() {
    private var _remainingTime = MutableStateFlow(30L)
    val remainingTime: StateFlow<Long> get() = _remainingTime

    private var countDownTimer: CountDownTimer? = null

    private val _gameResult = MutableStateFlow<GameResult?>(null) // 초기값 null
    val gameResult: StateFlow<GameResult?> = _gameResult
    private val _gainedStatus = MutableStateFlow<Map<GainedStatus, Int>?>(null)
    val gainedStatus: StateFlow<Map<GainedStatus, Int>?> get() = _gainedStatus

    init {
        webSocketManager.setOnMessageReceived { message ->
            when (message) {
                is StartGameData -> {
                    val currentTimeMillis: Long = System.currentTimeMillis()
                    startCountdown(getRemainingTime(currentTimeMillis, message.room.endTimestamp.time))
                }

                is ScoreUpdateData -> {
                    Log.d(Constant.TAG, "상대 운동함.")
                    addRivalCount(message.scoreChangeVolume)
                }

                is EndGameData -> {
                    _gainedStatus.value = message.gainedStats

                    if (!message.draw) {
                        if (message.winner.keys.contains(BattleConstants.USER_STATUS.id) ) {
                            // 승리
                            _gameResult.value = GameResult.WIN
                            Log.d(Constant.TAG,"마약, 범죄 연류 X 승리승리승리승리승리승리승리승리승리승리승리승리승리승리승리승리승리승리")
                        } else {
                            // 패배
                            _gameResult.value = GameResult.LOSE
                            Log.d(Constant.TAG,"패배패배패배패배패배패배패배패배패배패배패배패배패배패배패배패배패배패배패배패배패배패배패배")
                        }
                    } else {
                        // 무승부
                        _gameResult.value = GameResult.DRAW
                        Log.d(Constant.TAG,"무승부무승부무승부무승부무승부무승부무승부무승부무승부무승부무승부무승부무승부무승부무승부무승부무승부무승부")
                    }
                }

                else -> Log.d(Constant.TAG, "MuscleBattleViewModel 화난다. $message")
            }
        }
    }

    private fun clearBattleConstants() {
        BattleConstants.ROOM_ID = ""
        BattleConstants.RIVAL_STATUS = User("","","","")
        BattleConstants.USER_STATUS = User("","","","")
        BattleConstants.MATCH_TYPE = MatchType.SHORTSQUAT
    }

    // 타이머 시작
    private fun startCountdown(setTime: Long) {
        countDownTimer = object : CountDownTimer(setTime, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                // 매 1초마다 남은 시간 갱신
                Log.d(Constant.TAG, "startCountDown countDownTimer is ${_remainingTime.value}")
                _remainingTime.value = millisUntilFinished / 1000
            }

            override fun onFinish() {
                // 타이머가 끝났을 때 남은 시간을 0으로 설정
                _remainingTime.value = 0
            }
        }.start()
    }

    private fun getRemainingTime(currentTimeMillis: Long, endTimestamp: Long): Long {
        val remainingTimeMillis = endTimestamp - currentTimeMillis

        // 남은 시간 차이를 초 단위로 변환
        return TimeUnit.MILLISECONDS.toSeconds(remainingTimeMillis)
    }

    fun clearGameResult() {
        _gameResult.value = null
    }

    override fun onCleared() {
        super.onCleared()
        webSocketManager.stop()
        countDownTimer?.cancel()
        clearBattleConstants()
    }

    //============================================//============================================
    //============================================//============================================

    val userInfo: User get() = BattleConstants.USER_STATUS
    val rivalInfo: User get() = BattleConstants.RIVAL_STATUS

    private val _myCount = MutableStateFlow<Int>(0)
    private val _rivalCount = MutableStateFlow<Int>(0)

    val myCount: StateFlow<Int>
        get() = _myCount

    val rivalCount: StateFlow<Int>
        get() = _rivalCount

    private val _fitState = MutableLiveData<PoseType>(PoseType.DOWN)

    val fitState: LiveData<PoseType>
        get() = _fitState

    fun addMyCount() {
        val currentTimeMillis: Long = System.currentTimeMillis()
        _myCount.value += 1

        webSocketManager.sendScoreUpdate(
            roomId = BattleConstants.ROOM_ID,
            timestamp = currentTimeMillis,
            score = _myCount.value,
            scoreChangeVolume = 1,
            userId = BattleConstants.USER_STATUS.id
        )
    }

    fun giveUp() {
        webSocketManager.sendGiveUp()
    }

    private fun addRivalCount(addInt: Int) {
        _rivalCount.value += addInt
    }

    fun setFitState(poseType: PoseType) {
        _fitState.value = poseType
    }
}