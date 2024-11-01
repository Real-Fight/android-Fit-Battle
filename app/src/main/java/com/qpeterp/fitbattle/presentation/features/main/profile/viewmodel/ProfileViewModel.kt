package com.qpeterp.fitbattle.presentation.features.main.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.domain.model.battle.history.BattleHistory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    // 더미 데이터를 MutableStateFlow로 정의
    private val _dummyDataList = MutableStateFlow(
        listOf(
            BattleHistory(
                true,
                "단기전",
                "123회",
            ),
            BattleHistory(
                false,
                "중기전",
                "13회",
            ),
            BattleHistory(
                true,
                "단기전",
                "123회",
            ),
            BattleHistory(
                true,
                "단기전",
                "123회",
            ),
            BattleHistory(
                false,
                "단기전",
                "123회",
            ),
            BattleHistory(
                true,
                "단기전",
                "123회",
            ),
            BattleHistory(
                true,
                "단기전",
                "123회",
            ),
            BattleHistory(
                true,
                "단기전",
                "123회",
            ),
        )
    )

    // 외부에서 접근할 수 있는 StateFlow로 변환
    val dummyDataList: StateFlow<List<BattleHistory>> = _dummyDataList.asStateFlow()
}