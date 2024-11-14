package com.qpeterp.fitbattle.domain.model.train

import kotlinx.serialization.Serializable

@Serializable
data class TrainHistory(
    val trainingType: String,
    val count: Int,
    val createdAt: String,
)