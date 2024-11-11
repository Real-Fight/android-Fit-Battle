package com.qpeterp.fitbattle.common

import com.qpeterp.fitbattle.data.utils.TimestampSerializer
import com.qpeterp.fitbattle.data.utils.UUIDSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.sql.Timestamp
import java.util.UUID

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    allowSpecialFloatingPointValues = true
    encodeDefaults = true
    serializersModule = SerializersModule {
        contextual(UUID::class, UUIDSerializer)
        contextual(Timestamp::class, TimestampSerializer)
    }
}