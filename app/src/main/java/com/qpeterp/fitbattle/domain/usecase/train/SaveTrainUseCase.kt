package com.qpeterp.fitbattle.domain.usecase.train

import com.qpeterp.fitbattle.domain.model.train.SaveTrainData
import com.qpeterp.fitbattle.domain.model.train.TrainType
import com.qpeterp.fitbattle.domain.repository.TrainRepository
import retrofit2.Response
import javax.inject.Inject

class SaveTrainUseCase @Inject constructor(
    private val trainRepository: TrainRepository,
) {
    suspend operator fun invoke(param: Param): Result<Response<Unit>> {
        return runCatching {
            trainRepository.saveTrain(
                trainingType = param.trainType,
                count = param.saveTrain.count
            )
        }
    }

    data class Param(
        val trainType: TrainType,
        val saveTrain: SaveTrainData,
    )
}