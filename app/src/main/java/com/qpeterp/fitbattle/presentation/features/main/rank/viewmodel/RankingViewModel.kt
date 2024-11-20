package com.qpeterp.fitbattle.presentation.features.main.rank.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.domain.model.rank.Rank
import com.qpeterp.fitbattle.domain.usecase.rank.RankUseCase
import com.qpeterp.fitbattle.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class RankingUiState(
    val myRankInfo: Rank = Rank(
        id = UUID.randomUUID(),
        name = "...",
        profileImgUrl = "",
        ranking = 0,
        totalPower = 0,
        statusMessage = ""
    ),
    val rankingList: List<Rank> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val rankUseCase: RankUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RankingUiState())
    val uiState: StateFlow<RankingUiState> = _uiState

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val myRankResult = async { getMyRankInfo() }
            val rankListResult = async { getRankingList() }

            _uiState.value = _uiState.value.copy(
                myRankInfo = myRankResult.await() ?: _uiState.value.myRankInfo,
                rankingList = rankListResult.await() ?: emptyList(),
                isLoading = false
            )
        }
    }

    private suspend fun getMyRankInfo(): Rank? {
        return userUseCase()
            .getOrNull() // Result에서 성공 값(User)을 추출, 실패 시 null 반환
            ?.let { user ->
                Rank(
                    id = user.id,
                    name = user.name,
                    profileImgUrl = user.profileImgUrl,
                    ranking = user.ranking,
                    totalPower = user.totalPower,
                    statusMessage = user.statusMessage
                )
            }
            .also {
                if (it == null) Log.e(Constant.TAG, "GetMyRankInfo error")
            }
    }

    private suspend fun getRankingList(): List<Rank>? {
        return rankUseCase(RankUseCase.Param(0))
            .getOrNull() // Result에서 성공 값(List<Rank>)을 추출, 실패 시 null 반환
            ?.also { rankingList ->
                Log.d(Constant.TAG, "RankList: $rankingList")
            }
            .also {
                if (it == null) Log.e(Constant.TAG, "GetRankingList error")
            }
    }
}
