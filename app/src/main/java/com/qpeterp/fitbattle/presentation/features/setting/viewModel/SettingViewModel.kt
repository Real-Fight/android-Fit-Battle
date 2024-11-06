package com.qpeterp.fitbattle.presentation.features.setting.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.application.MyApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
) : ViewModel() {
    private val _ttsState = mutableStateOf(MyApplication.prefs.ttsState)
    val ttsState get() = _ttsState.value

    fun updateTtsState(state: Boolean) {
        MyApplication.prefs.ttsState = state
        _ttsState.value = state
    }
}