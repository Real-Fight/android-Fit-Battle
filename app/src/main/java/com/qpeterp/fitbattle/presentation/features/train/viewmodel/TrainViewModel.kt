package com.qpeterp.fitbattle.presentation.features.train.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.domain.model.pose.PoseType
import com.qpeterp.fitbattle.domain.model.train.TrainType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrainViewModel @Inject constructor(
): ViewModel() {
    private val _count = MutableLiveData<Int>(0)
    private val _squatState = MutableLiveData<PoseType>(PoseType.DOWN)
    private val _trainType = MutableLiveData<TrainType>(TrainType.SQUAT)

    val count: LiveData<Int>
        get() = _count

    val squatState: LiveData<PoseType>
        get() = _squatState

    val trainType: LiveData<TrainType>
        get() = _trainType

    fun addCount() {
        _count.value = _count.value!! + 1
    }

    fun setFitState(poseType: PoseType) {
        _squatState.value = poseType
    }

    fun setTrainType(trainType: TrainType) {
        _trainType.value = trainType
    }
}