package com.qpeterp.fitbattle.domain.repository

import com.qpeterp.fitbattle.domain.model.rank.Rank

interface RankRepository {
    suspend fun getRanking(
        page: Int
    ): List<Rank>
}