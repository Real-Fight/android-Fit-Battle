package com.qpeterp.fitbattle.domain.model.rank

import com.qpeterp.fitbattle.domain.model.user.User

data class Rank(
    val ranking: Int,
    val profile: String,
    val name: String,
//    val user: User,
    val score: Long
)
