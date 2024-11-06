package com.qpeterp.fitbattle.data.repository

import android.util.Log
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.data.remote.service.RankService
import com.qpeterp.fitbattle.domain.model.rank.Rank
import com.qpeterp.fitbattle.domain.repository.RankRepository
import javax.inject.Inject

class RankRepositoryImpl @Inject constructor(
    private val rankService: RankService
) : RankRepository {
    override suspend fun getRanking(page: Int): List<Rank> {
        val response = rankService.ranking(page)

        if (response.isSuccessful) {
            Log.d(Constant.TAG, "get ranking list : ${response.body()?.data}")
            return response.body()?.data ?: emptyList()
        }
        return emptyList()
    }
}