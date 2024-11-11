package com.qpeterp.fitbattle.data.socket.data

import kotlinx.serialization.Serializable

@Serializable
enum class MatchType {
    SHORTSQUAT,
    MIDDLESQUAT,
    LONGSQUAT,
    SHORTPUSHUP,
    MIDDLEPUSHUP,
    LONGPUSHUP,
}
