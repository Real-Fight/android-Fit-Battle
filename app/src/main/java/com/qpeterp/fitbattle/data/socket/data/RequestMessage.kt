package com.qpeterp.fitbattle.data.socket.data

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class RequestWrapper(
    val event: String,
    val data: RequestMessage?,
)

@Serializable(with = RequestMessageSerializer::class)
sealed class RequestMessage {
    @Serializable
    data class RandomMatchData(
        val token: String,
        val matchType: MatchType,
    ) : RequestMessage()

    // 나머지 요청 데이터 클래스는 동일
    @Serializable
    data class ReadiedData(
        val roomId: String,
    ) : RequestMessage()

    @Serializable
    data class ScoreUpdateData(
        val roomId: String,
        val timestamp: Long,
        val score: Int,
        val scoreChangeVolume: Int,
        val id: String,
    ) : RequestMessage()

    @Serializable
    data object EmptyData : RequestMessage()
}

class RequestMessageSerializer :
    JsonContentPolymorphicSerializer<RequestMessage>(RequestMessage::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out RequestMessage> {
        return when (element.jsonObject["event"]?.jsonPrimitive?.content) {
            "SCOREUPDATE" -> RequestMessage.ScoreUpdateData.serializer()
            "RANDOMMATCH" -> RequestMessage.RandomMatchData.serializer()
            "READIED" -> RequestMessage.ReadiedData.serializer()
            else -> throw SerializationException("Unknown event: ${element.jsonObject["event"]}")
        }
    }
}