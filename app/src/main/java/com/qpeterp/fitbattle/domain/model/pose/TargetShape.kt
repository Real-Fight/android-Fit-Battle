package com.qpeterp.fitbattle.domain.model.pose

data class TargetShape(
    val firstLandmarkType: Int,
    val middleLandmarkType: Int,
    val lastLandmarkType: Int,
    val angle: Double
)

data class TargetPose(
    val targets: List<TargetShape>
)