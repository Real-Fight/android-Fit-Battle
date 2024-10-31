package com.qpeterp.fitbattle.presentation.features.main.ranking.viewmodel

import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.domain.model.rank.Rank
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RankingViewModel : ViewModel() {
    // 더미 데이터를 MutableStateFlow로 정의
    private val _dummyDataList = MutableStateFlow(
        listOf(
            Rank(
                1,
                "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
                "이성은 그는 신인가?",
                1234343
            ),
            Rank(
                2,
                "https://images.unsplash.com/photo-1501594907352-04cda38ebc29",
                "이성은 그는 신인가?",
                123434
            ),
            Rank(
                3,
                "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
                "이성은 그는 신인가?",
                12343
            ),
            Rank(
                4,
                "https://images.unsplash.com/photo-1501594907352-04cda38ebc29",
                "이성은 그는 신인가?",
                123
            ),

            )
    )

    // 외부에서 접근할 수 있는 StateFlow로 변환
    val dummyDataList: StateFlow<List<Rank>> = _dummyDataList.asStateFlow()
}