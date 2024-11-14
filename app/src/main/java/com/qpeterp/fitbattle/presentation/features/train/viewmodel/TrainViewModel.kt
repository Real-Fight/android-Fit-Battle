package com.qpeterp.fitbattle.presentation.features.train.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.domain.model.pose.PoseType
import com.qpeterp.fitbattle.domain.model.train.SaveTrainData
import com.qpeterp.fitbattle.domain.model.train.TrainHistory
import com.qpeterp.fitbattle.domain.model.train.TrainType
import com.qpeterp.fitbattle.domain.usecase.train.SaveTrainUseCase
import com.qpeterp.fitbattle.domain.usecase.train.TrainHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TrainViewModel @Inject constructor(
    private val saveTrainUseCase: SaveTrainUseCase,
    private val trainHistoryUseCase: TrainHistoryUseCase,
) : ViewModel() {
    private val _trainHistoryList = MutableStateFlow<List<TrainHistory>?>(null)
    val trainHistoryList get() = _trainHistoryList

    private val _count = MutableLiveData<Long>(0)
    private val _fitState = MutableLiveData<PoseType>(PoseType.DOWN)
    private val _trainType = MutableLiveData<TrainType>(TrainType.SQUAT)

    private val _angle = MutableLiveData<Float>(0.0f)
    val angle: LiveData<Float> get() = _angle

    fun setAngle(angle: Float) {
        _angle.value = angle
    }


    val count: LiveData<Long>
        get() = _count

    val fitState: LiveData<PoseType>
        get() = _fitState

    val trainType: LiveData<TrainType>
        get() = _trainType

    fun addCount() {
        _count.value = _count.value!! + 1
    }

    fun setFitState(poseType: PoseType) {
        _fitState.value = poseType
    }

    suspend fun setTrainType(trainType: TrainType) {
        getTrainHistory(trainType)
        _trainType.value = trainType
    }

    private suspend fun getTrainHistory(trainType: TrainType) {
        trainHistoryUseCase(
            param = TrainHistoryUseCase.Param(trainingType = trainType)
        ).onSuccess {
            Log.d(Constant.TAG, "TrainViewModel trainHistoryUseCase data : $it")
            _trainHistoryList.value = it
        }.onFailure {
            Log.d(Constant.TAG, "TrainViewModel trainHistoryUseCase errorororrororo : $it")
        }
    }

    suspend fun saveTrain() {
        saveTrainUseCase(
            param = SaveTrainUseCase.Param(
                trainType = _trainType.value!!,
                saveTrain = SaveTrainData(
                    count = _count.value ?: 0,
                    trainingType = _trainType.value!!.name,
                )
            )
        ).onSuccess {
            Log.d(Constant.TAG, "saveTrain is success")
        }.onFailure {
            Log.d(Constant.TAG, "saveTrain is failure")
        }
    }
}