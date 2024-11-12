package com.qpeterp.fitbattle.presentation.features.main.rank.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.domain.model.rank.Rank
import com.qpeterp.fitbattle.domain.usecase.rank.RankUseCase
import com.qpeterp.fitbattle.domain.usecase.user.UserProfileUseCase
import com.qpeterp.fitbattle.domain.usecase.user.UserStatusUseCase
import com.qpeterp.fitbattle.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val rankUseCase: RankUseCase,
    private val userUseCase: UserUseCase,
) : ViewModel() {
    private val _myRankInfo = MutableStateFlow(
        Rank(
            UUID.randomUUID(),
            "...",
            "",
            0,
            "",
            totalPower = 0
        )
    )
    val myRankInfo get() = _myRankInfo

    private val _rankingList = MutableStateFlow<MutableList<Rank>>(mutableListOf())
    val rankingList get() = _rankingList.value

    private val _isLoading = MutableStateFlow(true) // 로딩 상태 관리
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _page = MutableStateFlow(0)
    val page get() = _page.value

    fun addRankingList(rankingList: List<Rank>) {
        _rankingList.value.addAll(rankingList)
    }

    private fun setRankingList(rankingList: List<Rank>) {
        _rankingList.value.clear()
        _rankingList.value.addAll(rankingList)
    }

    suspend fun getRankingList() {
        _isLoading.value = true
        rankUseCase(RankUseCase.Param(_page.value))
            .onSuccess {
                Log.d(Constant.TAG, "RankList : $it")
                setRankingList(it)
                _isLoading.value = false
            }.onFailure {
                Log.d(Constant.TAG, "getRankList error : $it")
                setRankingList(emptyList())
                _isLoading.value = false
            }
    }

    suspend fun getMyRankInfo() {
        _isLoading.value = true
        userUseCase()
            .onSuccess {
                _myRankInfo.value = Rank(
                    id = it.id,
                    name = it.name,
                    ranking = it.ranking,
                    profileImgUrl = it.profileImgUrl,
                    totalPower = it.totalPower,
                    statusMessage = it.statusMessage
                )
                _isLoading.value = false
            }
            .onFailure {
                Log.d(Constant.TAG, "GetMyRankInfo error : $it")
                _isLoading.value = false
            }
    }
}