package com.qpeterp.fitbattle.data.socket.data

import android.util.Log
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.common.json as commonJson
import kotlinx.serialization.Contextual
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.sql.Timestamp
import java.util.UUID

@Serializable(with = ResponseMessageSerializer::class)
sealed class ResponseMessage

@Serializable
data object EmptyData : ResponseMessage()

@Serializable
data class MatchedData(
    val rivalId: String,
    val rivalProfileImgUrl: String,
    val rivalName: String,
    val rivalRanking: Long,
    val userId: String,
    val userProfileImgUrl: String,
    val userName: String,
    val userRanking: Long,
    val roomId: String
) : ResponseMessage()

@Serializable
data class StartGameData(
    val room: RoomData
) : ResponseMessage()

@Serializable
data class ScoreUpdateData(
    val roomId: String,
    val timestamp: Long,
    val score: Int,
    val scoreChangeVolume: Int,
) : ResponseMessage()

@Serializable
data class EndGameData(
    val winner: Map<String, Int>,
    val loser: Map<String, Int>,
    val draw: Boolean
) : ResponseMessage()

enum class Status {
    READY, PROGRESS
}

enum class UserStatus {
    READY, ON
}

@Serializable
data class RoomData(
    val matchType: MatchType,
    var status: Status,
    @Contextual val endTimestamp: Timestamp,
    val scores: MutableMap<@Contextual UUID, Int>,
    val users: MutableMap<@Contextual UUID, UserStatus>
)

object ResponseMessageSerializer : JsonContentPolymorphicSerializer<ResponseMessage>(ResponseMessage::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out ResponseMessage> {
        val json = element.jsonObject
        val event = json["event"]?.jsonPrimitive?.content
            ?: throw SerializationException("Missing 'event' field in JSON: $json")

        return when (event) {
            "MATCHOK" -> EmptyData.serializer()
            "MATCHED" -> MatchedData.serializer()  // MatchedData 선택
            "STARTGAME" -> StartGameData.serializer()
            "SCOREUPDATE" -> {
                val data = json["data"] ?: throw SerializationException("Missing 'data' field in JSON: $json")
                Log.d(Constant.TAG, "scoreUpdate is ${data.jsonObject}")
                ScoreUpdateData.serializer()
            }
            "ENDGAME" -> EndGameData.serializer()
            "MATCHINGCANCELED" -> EmptyData.serializer()
            else -> throw SerializationException("Unknown event: $event")
        }
    }

    fun parseMessage(jsonElement: JsonElement): ResponseMessage {
        val json = jsonElement.jsonObject
        val data = json["data"] ?: throw SerializationException("Missing 'data' field in JSON: $json")

        val deserializer = selectDeserializer(jsonElement)

        // 해당 'data'를 맞는 타입으로 파싱하여 반환
        return commonJson.decodeFromJsonElement(deserializer, data)
    }
}