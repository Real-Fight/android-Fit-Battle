package com.qpeterp.fitbattle.presentation.features.main.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {
    private val _selectedTab = mutableIntStateOf(0)
    val selectedTab get() = _selectedTab.intValue

    fun updateSelectedTab(tab: Int) {
        _selectedTab.intValue = tab
    }
}