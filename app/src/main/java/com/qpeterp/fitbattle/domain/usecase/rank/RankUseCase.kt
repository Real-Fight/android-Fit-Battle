package com.qpeterp.fitbattle.domain.usecase.rank

import com.qpeterp.fitbattle.domain.model.rank.Rank
import com.qpeterp.fitbattle.domain.repository.RankRepository
import javax.inject.Inject

class RankUseCase @Inject constructor(
    private val rankRepository: RankRepository
) {
    suspend operator fun invoke(param: Param): Result<List<Rank>> {
        return runCatching {
            rankRepository.getRanking(param.page)
        }
    }

    data class Param(
        val page: Int
    )
}