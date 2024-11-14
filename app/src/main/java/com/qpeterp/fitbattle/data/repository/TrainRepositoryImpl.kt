package com.qpeterp.fitbattle.data.repository

import android.util.Log
import com.qpeterp.fitbattle.application.MyApplication
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.data.remote.service.TrainService
import com.qpeterp.fitbattle.domain.model.train.SaveTrainData
import com.qpeterp.fitbattle.domain.model.train.TrainHistory
import com.qpeterp.fitbattle.domain.model.train.TrainType
import com.qpeterp.fitbattle.domain.repository.TrainRepository
import retrofit2.Response
import javax.inject.Inject

class TrainRepositoryImpl @Inject constructor(
    private val trainService: TrainService,
) : TrainRepository {
    override suspend fun getTrainHistory(
        trainingType: TrainType,
    ): List<TrainHistory> {
        val response = trainService.trainHistory(authorization = MyApplication.prefs.token, trainingType = trainingType)

        if (response.isSuccessful) {
            Log.d(Constant.TAG, "getTrainHistory is ${response.body()}")
            return response.body()!!.data
        } else {
            Log.e(Constant.TAG, "Error fetching train history: ${response.errorBody()?.string()}")
            throw Exception("Failed to fetch user data: ${response.errorBody()?.string()}")
        }
    }

    override suspend fun saveTrain(
        trainType: TrainType,
        count: Long,
    ): Response<Unit> {
        Log.d(Constant.TAG, "trianType: $trainType, count : $count")
        val response = trainService.saveTrain(
            authorization = MyApplication.prefs.token,
            count = count,
            trainingType = trainType.name
        )

        if (response.isSuccessful) {
            Log.d(Constant.TAG, "saveTrain is ${response.body()}")
            return response
        } else {
            Log.e(Constant.TAG, "Error fetching saveTrain : ${response.errorBody()?.string()}")
            throw Exception("Failed to fetch saveTrain: ${response.errorBody()?.string()}")
        }
    }
}