package com.qpeterp.fitbattle.domain.model.train

enum class TrainType(
    val label: String,
    val isVertical: Boolean
) {
    SQUAT(
        "스쿼트",
        true
    ),
    PUSH_UP(
        "푸쉬업",
        false
    ),
    SIT_UP(
        "윗몸 일으키기",
        false
    ),
    RUN(
        "달리기",
        true
    )
}