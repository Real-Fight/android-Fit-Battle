package com.qpeterp.fitbattle.presentation.features.train.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TrainViewModel: ViewModel() {
    private val _count = mutableStateOf("")
    val count get() = _count.value

    fun addCount() {
        _count.value += 1
    }
}