package com.qpeterp.fitbattle.presentation.features.main.rank.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.domain.model.rank.Rank
import com.qpeterp.fitbattle.domain.usecase.rank.RankUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val rankUseCase: RankUseCase
) : ViewModel() {
    private val _rankingList = MutableStateFlow<MutableList<Rank>>(mutableListOf())
    val rankingList get() = _rankingList.value

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
        rankUseCase(RankUseCase.Param(_page.value))
            .onSuccess {
                Log.d(Constant.TAG, "RankList : $it")
                setRankingList(it)
            }.onFailure {
                Log.d(Constant.TAG, "getRankList error : $it")
                setRankingList(emptyList())
            }
    }
}