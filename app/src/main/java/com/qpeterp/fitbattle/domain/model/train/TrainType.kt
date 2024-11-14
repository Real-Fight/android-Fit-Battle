package com.qpeterp.fitbattle.domain.model.train

enum class TrainType(
    val label: String,
    val isVertical: Boolean
) {
    SQUAT(
        "스쿼트",
        true
    ),
    PUSHUP(
        "푸쉬업",
        false
    ),
    SITUP(
        "윗몸 일으키기",
        false
    ),
    RUN(
        "달리기",
        true
    )
}