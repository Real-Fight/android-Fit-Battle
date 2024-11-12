package com.qpeterp.fitbattle.data.socket

import com.qpeterp.fitbattle.BuildConfig
import com.qpeterp.fitbattle.application.MyApplication
import com.qpeterp.fitbattle.common.json
import com.qpeterp.fitbattle.data.socket.data.Event
import com.qpeterp.fitbattle.data.socket.data.MatchType
import com.qpeterp.fitbattle.data.socket.data.RequestMessage
import com.qpeterp.fitbattle.data.socket.data.RequestWrapper
import com.qpeterp.fitbattle.data.socket.data.ResponseMessage
import kotlinx.serialization.encodeToString
import okhttp3.*
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Singleton
class WebSocketManager @Inject constructor() {
    // WebSocket 초기 설정 부분은 동일
    private fun createUnsafeSslSocketFactory(): SSLSocketFactory {
        val trustAllCertificates = arrayOf<TrustManager>(createUnsafeTrustManager())
        val context = SSLContext.getInstance("TLS")
        context.init(null, trustAllCertificates, SecureRandom())
        return context.socketFactory
    }

    private fun createUnsafeTrustManager(): X509TrustManager {
        return object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String?) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String?) {}
        }
    }

    private val client = OkHttpClient.Builder()
        .sslSocketFactory(createUnsafeSslSocketFactory(), createUnsafeTrustManager())
        .hostnameVerifier { _, _ -> true }
        .build()
    private lateinit var webSocket: WebSocket
    private val request: Request = Request.Builder()
        .url(BuildConfig.SOCKET_URL)
        .build()

    // 콜백 설정
    private var onConnect: (() -> Unit)? = null
    private var onMessageReceived: ((ResponseMessage) -> Unit)? = null

    // WebSocketListener 생성 시 콜백 전달
    private val webSocketListener = MyWebSocketListener { responseMessage ->
        onMessageReceived?.invoke(responseMessage)
    }

    // WebSocket 시작
    fun start() {
        webSocket = client.newWebSocket(request, webSocketListener)
    }

    // WebSocket 종료
    fun stop() {
        webSocket.close(1000, "종료")
    }

    fun setOnMessageReceived(listener: (ResponseMessage) -> Unit) {
        onMessageReceived = listener
    }

    // 메시지 전송 예시 (RandomMatch)
    fun sendRandomMatch(matchType: MatchType) {
        val message = RequestMessage.RandomMatchData(
            token = MyApplication.prefs.token,
            matchType = matchType
        )

        val request = RequestWrapper(
            event = Event.RANDOMMATCH.toString(),
            data = message
        )
        val jsonMessage = json.encodeToString(request)
        webSocket.send(jsonMessage)
    }

    fun sendMatchCancel() {
        val request = RequestWrapper(
            event = Event.MATCHINGCANCEL.toString(),
            data = RequestMessage.EmptyData
        )

        val jsonMessage = json.encodeToString(request)
        webSocket.send(jsonMessage)
    }

    fun sendReadied(roomId: String) {
        val message = RequestMessage.ReadiedData(roomId = roomId)

        val request = RequestWrapper(
            event = Event.READIED.toString(),
            data = message
        )

        val jsonMessage = json.encodeToString(request)
        webSocket.send(jsonMessage)
    }

    fun sendScoreUpdate(
        roomId: String,
        timestamp: Long,
        score: Int,
        scoreChangeVolume: Int,
        userId: String
    ) {
        val message = RequestMessage.ScoreUpdateData(
            roomId = roomId,
            timestamp = timestamp,
            score = score,
            scoreChangeVolume = scoreChangeVolume,
            id = userId
        )

        val request = RequestWrapper(
            event = Event.SCOREUPDATE.toString(),
            data = message
        )

        val jsonMessage = json.encodeToString(request)
        webSocket.send(jsonMessage)
    }

    fun sendGiveUp() {
        val request = RequestWrapper(
            event = Event.GIVEUP.toString(),
            data = RequestMessage.EmptyData
        )

        val jsonMessage = json.encodeToString(request)
        webSocket.send(jsonMessage)
    }
}
