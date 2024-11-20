package com.qpeterp.fitbattle.presentation.features.main.profile.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.domain.model.battle.history.BattleHistory
import com.qpeterp.fitbattle.domain.model.user.User
import com.qpeterp.fitbattle.domain.usecase.user.HistoryUseCase
import com.qpeterp.fitbattle.domain.usecase.user.UserProfileUseCase
import com.qpeterp.fitbattle.domain.usecase.user.UserStatusUseCase
import com.qpeterp.fitbattle.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val myRankInfo: User? = null,
    val historyList: List<BattleHistory>? = null,
    val isLoading: Boolean = true
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val userStatusUseCase: UserStatusUseCase,
    private val userProfileUseCase: UserProfileUseCase,
    private val historyUseCase: HistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val myRankDeferred = async { fetchMyRankInfo() }
            val historyDeferred = async { fetchHistory() }

            _uiState.value = _uiState.value.copy(
                myRankInfo = myRankDeferred.await(),
                historyList = historyDeferred.await(),
                isLoading = false
            )
        }
    }

    private suspend fun fetchMyRankInfo(): User? {
        return userUseCase()
            .getOrNull() // 성공 시 User 반환, 실패 시 null
            .also {
                if (it == null) Log.e(Constant.TAG, "FetchMyRankInfo error")
            }
    }

    private suspend fun fetchHistory(): List<BattleHistory>? {
        return historyUseCase()
            .getOrNull() // 성공 시 List<BattleHistory> 반환, 실패 시 null
            .also {
                if (it == null) Log.e(Constant.TAG, "FetchHistory error")
            }
    }

    fun patchStatusMessage(statusMessage: String) {
        viewModelScope.launch {
            userStatusUseCase(UserStatusUseCase.Param(statusMessage))
                .onFailure {
                    Log.e(Constant.TAG, "PatchStatusMessage error: $it")
                }
        }
    }

    fun patchProfile(profile: Bitmap) {
        viewModelScope.launch {
            userProfileUseCase(UserProfileUseCase.Param(profile))
                .onFailure {
                    Log.e(Constant.TAG, "PatchProfile error: $it")
                }
        }
    }
}
