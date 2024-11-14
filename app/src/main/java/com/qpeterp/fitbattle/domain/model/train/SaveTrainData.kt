package com.qpeterp.fitbattle.domain.model.train

import kotlinx.serialization.Serializable

@Serializable
data class SaveTrainData(
    val trainingType: String,
    val count: Long = 0
)
