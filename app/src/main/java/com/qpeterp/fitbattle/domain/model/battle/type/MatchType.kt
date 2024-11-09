package com.qpeterp.fitbattle.domain.model.battle.type

enum class MatchType(val length: String, val type: String) {
    SHORTSQUAT("단기전", "스쿼트"),
    MIDDLESQUAT("중기전", "스쿼트"),
    LONGSQUAT("장기전", "스쿼트"),
    SHORTPUSHUP("단기전", "푸쉬업"),
    MIDDLEPUSHUP("중기전", "푸쉬업"),
    LONGPUSHUP("장기전", "푸쉬업")
}