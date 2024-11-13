package com.qpeterp.fitbattle.presentation.features.main.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.domain.model.user.Quest
import com.qpeterp.fitbattle.domain.model.user.QuestType
import com.qpeterp.fitbattle.domain.usecase.user.QuestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val questUseCase: QuestUseCase,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true) // 로딩 상태 관리
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _quest = MutableStateFlow<Quest?>(null)
    val quest: StateFlow<Quest?> = _quest

    suspend fun getQuest() {
        _isLoading.value = true
        questUseCase()
            .onSuccess {
                _quest.value = it
                _isLoading.value = false
            }
            .onFailure {
                _quest.value = Quest(
                    message = "운동한판 2회 (0/2)",
                    completed = false,
                    questType = QuestType.MATCH
                )
                Log.d(Constant.TAG, "getQuest error : $it")
                _isLoading.value = false
            }
    }
}