package com.qpeterp.fitbattle.presentation.features.main.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.domain.model.user.Quest
import com.qpeterp.fitbattle.domain.model.user.QuestType
import com.qpeterp.fitbattle.domain.usecase.user.QuestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val quest: Quest? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val questUseCase: QuestUseCase,
) : ViewModel() {

    // UI 상태를 관리하는 StateFlow
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    // 데이터 로드 함수
    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)  // 로딩 시작

            // 비동기로 퀘스트 정보와 로딩을 처리
            val questDeferred = async { fetchQuest() }

            // 데이터 업데이트
            _uiState.value = _uiState.value.copy(
                quest = questDeferred.await(),
                isLoading = false  // 로딩 종료
            )
        }
    }

    // 퀘스트 정보를 가져오는 함수
    private suspend fun fetchQuest(): Quest? {
        return questUseCase()
            .getOrNull()  // 성공하면 Quest 객체 반환, 실패하면 null 반환
            .also {
                if (it == null) Log.e(Constant.TAG, "FetchQuest error")
            }
    }
}
