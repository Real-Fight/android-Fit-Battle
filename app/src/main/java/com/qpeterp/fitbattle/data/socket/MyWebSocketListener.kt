package com.qpeterp.fitbattle.data.socket

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.qpeterp.fitbattle.common.Constant
import com.qpeterp.fitbattle.common.json
import com.qpeterp.fitbattle.data.socket.data.ResponseMessage
import com.qpeterp.fitbattle.data.socket.data.ResponseMessageSerializer
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class MyWebSocketListener(
    private val onMessageReceived: (ResponseMessage) -> Unit
) : WebSocketListener() {

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("${Constant.TAG} WebSocket", "Connection opened")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("${Constant.TAG} WebSocket", "Message received: $text")

        try {
            val jsonElement = json.parseToJsonElement(text)
            // 해당 메시지에 맞는 ResponseMessage 타입으로 변환
            val responseMessage = ResponseMessageSerializer.parseMessage(jsonElement)
            // 메인 스레드에서 onMessageReceived 호출
            Handler(Looper.getMainLooper()).post {
                onMessageReceived(responseMessage)
            }
        } catch (e: Exception) {
            Log.e("${Constant.TAG} WebSocket", "Failed to parse message: ${e.message}")
            throw e
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("${Constant.TAG} WebSocket", "Connection closing: $reason")
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e("${Constant.TAG} WebSocket", "Connection error: ${t.message}")
    }
}