package com.qpeterp.fitbattle.domain.repository

import com.qpeterp.fitbattle.domain.model.train.TrainHistory
import com.qpeterp.fitbattle.domain.model.train.TrainType
import retrofit2.Response

interface TrainRepository {
    suspend fun getTrainHistory(
        trainingType: TrainType
    ): List<TrainHistory>

    suspend fun saveTrain(
        trainingType: TrainType,
        count: Long,
    ): Response<Unit>
}