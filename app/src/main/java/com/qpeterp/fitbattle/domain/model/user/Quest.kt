package com.qpeterp.fitbattle.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class Quest(
    val message: String,
    val completed: Boolean,
    val questType: QuestType
)

enum class QuestType {
    PUSHUP, SQUAT, MATCH, SITUP
}