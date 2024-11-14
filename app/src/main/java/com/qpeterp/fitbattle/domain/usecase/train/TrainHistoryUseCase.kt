package com.qpeterp.fitbattle.domain.usecase.train

import com.qpeterp.fitbattle.domain.model.train.TrainHistory
import com.qpeterp.fitbattle.domain.model.train.TrainType
import com.qpeterp.fitbattle.domain.repository.TrainRepository
import javax.inject.Inject

class TrainHistoryUseCase @Inject constructor(
    private val trainRepository: TrainRepository,
) {
    suspend operator fun invoke(param: Param): Result<List<TrainHistory>> {
        return runCatching {
            trainRepository.getTrainHistory(trainingType = param.trainingType)
        }
    }

    data class Param(
        val trainingType: TrainType,
    )
}