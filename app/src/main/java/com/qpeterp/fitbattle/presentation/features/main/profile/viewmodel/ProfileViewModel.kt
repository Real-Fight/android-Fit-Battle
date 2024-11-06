package com.qpeterp.fitbattle.presentation.features.main.profile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.domain.model.battle.history.BattleHistory
import com.qpeterp.fitbattle.domain.model.user.User
import com.qpeterp.fitbattle.domain.usecase.user.HistoryUseCase
import com.qpeterp.fitbattle.domain.usecase.user.UserProfileUseCase
import com.qpeterp.fitbattle.domain.usecase.user.UserStatusUseCase
import com.qpeterp.fitbattle.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val userStatusUseCase: UserStatusUseCase,
    private val userProfileUseCase: UserProfileUseCase,
    private val historyUseCase: HistoryUseCase
) : ViewModel() {
    private val _myRankInfo = MutableStateFlow<User?>(null)
    val myRankInfo get() = _myRankInfo.value

    private val _isLoading = MutableStateFlow(true) // 로딩 상태 관리
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _historyList = MutableStateFlow<List<BattleHistory>?>(null)
    val historyList get() = _historyList

    suspend fun getMyRankInfo() {
        _isLoading.value = true
        userUseCase()
            .onSuccess {
                _myRankInfo.value = it
                _isLoading.value = false
            }
            .onFailure {
                Log.d(Constant.TAG, "GetMyRankInfo error : $it")
                _isLoading.value = false
            }
    }

    suspend fun patchStatusMessage(statusMessage: String) {
        userStatusUseCase(
            UserStatusUseCase.Param(
                statusMessage = statusMessage
            )
        ).onSuccess {
        }.onFailure {
            Log.d(Constant.TAG, "patchStatusMessage error : $it")
        }
    }

    suspend fun getHistory() {
        _isLoading.value = true
        historyUseCase().onSuccess {
            _historyList.value = it
            _isLoading.value = false
        }.onFailure {
            Log.d(Constant.TAG, "getHistory error : $it")
            _isLoading.value = false
        }
    }
}